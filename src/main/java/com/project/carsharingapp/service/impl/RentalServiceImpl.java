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
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
    public List<RentalDto> getAllByUserIdAndActiveStatus(Long userId, boolean isActive) {
        return rentalRepository.findAllByUserIdAndActiveStatus(userId, isActive)
                .stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Rental getByUserIdAndActiveStatus(Long userId, boolean isActive) {
        return rentalRepository.findByUserIdAndActiveStatus(userId, isActive).orElseThrow(
                () -> new EntityNotFoundException("Can't find a rental by user id: " + userId));
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
        Rental rental1 = rentalRepository.findByUserIdAndActiveStatus(
                        user.getId(), true)
                .orElseThrow(() -> new EntityNotFoundException(" Active rental "
                        + "not found by id: " + user.getId()));
        sendNotification(rental1, authentication);
        return rentalRepository.findByUserIdAndActiveStatus(user.getId(), true)
                .map(rental -> {
                    rental.setActualReturnDate(LocalDateTime.now());
                    rental.setActive(false);
                    rentalRepository.save(rental);
                    increaseCarInventory(rental.getCar().getId());
                    return rentalMapper.toDto(rental);
                })
                .orElseThrow(() -> new EntityNotFoundException(" Active rental "
                        + "not found by id: " + user.getId()));
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

    private void sendNotification(Rental rental, Authentication authentication) {
        User user = getUser(authentication.getName());
        Long chatId = user.getTelegramChatId();
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
