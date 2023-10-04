package com.project.carsharingapp.service.rental;

import com.project.carsharingapp.dto.rental.CreateRentalRequestDto;
import com.project.carsharingapp.dto.rental.RentalDto;
import com.project.carsharingapp.exception.EntityNotFoundException;
import com.project.carsharingapp.mapper.RentalMapper;
import com.project.carsharingapp.model.Car;
import com.project.carsharingapp.model.Rental;
import com.project.carsharingapp.model.User;
import com.project.carsharingapp.repository.CarRepository;
import com.project.carsharingapp.repository.UserRepository;
import com.project.carsharingapp.repository.rentals.RentalRepository;
import java.time.LocalDateTime;
import java.util.List;
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

    @Override
    public RentalDto add(CreateRentalRequestDto requestDto, Authentication authentication) {
        Rental rental = rentalMapper.toEntity(requestDto);
        rental.setCar(getCar(requestDto.getCarId()));
        rental.setUser(getUser(authentication.getName()));
        rental.setActive(true);
        rental = rentalRepository.save(rental);
        decreaseCarInventory(requestDto.getCarId());
        return rentalMapper.toDto(rental);
    }

    @Override
    public List<RentalDto> getByUserIdAndActiveStatus(Long userId, boolean isActive) {
        return null;
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
        return rentalRepository.findRentalsByUserIdAndActiveStatus(user.getId(), true)
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
}
