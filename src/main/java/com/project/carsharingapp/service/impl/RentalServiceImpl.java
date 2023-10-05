package com.project.carsharingapp.service.impl;

import com.project.carsharingapp.dto.rental.CreateRentalRequestDto;
import com.project.carsharingapp.dto.rental.RentalDto;
import com.project.carsharingapp.exception.EntityNotFoundException;
import com.project.carsharingapp.mapper.RentalMapper;
import com.project.carsharingapp.model.Car;
import com.project.carsharingapp.model.Rental;
import com.project.carsharingapp.model.User;
import com.project.carsharingapp.repository.CarRepository;
import com.project.carsharingapp.repository.RentalRepository;
import com.project.carsharingapp.repository.UserRepository;
import com.project.carsharingapp.service.NotificationService;
import com.project.carsharingapp.service.RentalService;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Override
    public RentalDto add(CreateRentalRequestDto requestDto, Authentication authentication) {
        Rental rental = rentalMapper.toEntity(requestDto);
        rental.setCar(getCar(requestDto.getCarId()));
        rental.setUser(getUser(authentication.getName()));
        rental.setActive(true);
        rental = rentalRepository.save(rental);
        decreaseCarInventory(requestDto.getCarId());
        sendNotification(rental, authentication);
        return rentalMapper.toDto(rental);
    }

    @Override
    public List<RentalDto> getByUserIdAndActiveStatus(Pageable pageable,
                                                      Long userId,
                                                      Boolean isActive) {
        Specification<Rental> rentalSpecification = getSpecification(userId, isActive);
        return rentalRepository.findAll(rentalSpecification, pageable)
                .stream()
                .map(rentalMapper::toDto)
                .toList();
    }

    @Override
    public Rental getByUserAndId(User user, Long id) {
        return rentalRepository.findByUserIdAndId(user.getId(), id).orElseThrow(
                () -> new EntityNotFoundException("Can't find a rental with id: " + id
                                                    + " for the user")
        );
    }

    @Override
    public RentalDto getById(Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find rental by id: " + id)
        );
        return rentalMapper.toDto(rental);
    }

    @Override
    public RentalDto setActualReturnDay(Authentication authentication) {
        User user = getUser(authentication.getName());
        return rentalRepository.findByUserIdAndActiveStatus(user.getId(), true)
                .map(rental -> {
                    isCorrectReturnDate(rental);
                    rental.setActualReturnDate(LocalDateTime.now());
                    rental.setActive(false);
                    rentalRepository.save(rental);
                    increaseCarInventory(rental.getCar().getId());
                    sendNotification(rental, authentication);
                    return rentalMapper.toDto(rental);
                })
                .orElseThrow(() -> new EntityNotFoundException(" Active rental "
                        + "not found by id: " + user.getId()));
    }

    private static void isCorrectReturnDate(Rental rental) {
        if (rental.getRentalDate().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("The car can't be returned"
                    + " before rental date");
        }
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User "
                        + "not found by email: " + email));
    }

    private Car getCar(Long carId) {
        return carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car"
                        + " not found by id: " + carId));
    }

    private void decreaseCarInventory(Long carId) {
        Car car = getCar(carId);
        Integer existedInventory = car.getInventory();
        car.setInventory(existedInventory - 1);
        carRepository.save(car);
    }

    private void increaseCarInventory(Long carId) {
        Car car = getCar(carId);
        Integer existedInventory = car.getInventory();
        car.setInventory(existedInventory + 1);
        carRepository.save(car);
    }

    private boolean isValidActualReturnDate(Rental rental, LocalDateTime actualReturnData) {
        return rental.getRentalDate().isBefore(actualReturnData);
    }
  
    private Specification<Rental> getSpecification(Long userId, Boolean isActive) {
        return (root, query, criteriaBuilder) -> {
            Predicate userPredicate = (userId != null)
                    ? criteriaBuilder.equal(root.get("user").get("id"), userId)
                    : criteriaBuilder.conjunction();
            if (isActive != null) {
                if (isActive) {
                    return criteriaBuilder.and(userPredicate,
                            criteriaBuilder.isTrue(root.get("isActive")));
                }
                return criteriaBuilder.and(userPredicate,
                        criteriaBuilder.isFalse(root.get("isActive")));
            }
            return userPredicate;
        };
    }

    private void sendNotification(Rental rental, Authentication authentication) {
        User user = getUser(authentication.getName());
        Long chatId = user.getTelegramChatId();
        if (chatId != null) {
            String userEmail = user.getEmail();
            Car car = rental.getCar();
            StringBuilder message = new StringBuilder();
            message.append("You rental was created. Rental detail: \n")
                    .append("User: ")
                    .append(userEmail)
                    .append("\n Rental date: ")
                    .append(rental.getRentalDate())
                    .append("\n Return date: ")
                    .append(rental.getReturnDate())
                    .append("\n Car: ")
                    .append(car.getBrand())
                    .append(" ")
                    .append(car.getModel());
            if (rental.getActualReturnDate() != null) {
                message.append("\n Actual return data: ")
                        .append(rental.getActualReturnDate());
            }
            notificationService.sendMessage(chatId, message.toString());
        }
    }
}
