package com.project.carsharingapp.service.rental;

import com.project.carsharingapp.dto.rental.CreateRentalRequestDto;
import com.project.carsharingapp.dto.rental.RentalDto;
import com.project.carsharingapp.exception.EntityNotFoundException;
import com.project.carsharingapp.mapper.RentalMapper;
import com.project.carsharingapp.model.Car;
import com.project.carsharingapp.model.Rental;
import com.project.carsharingapp.model.User;
import com.project.carsharingapp.repository.CarRepository;
import com.project.carsharingapp.repository.rentals.RentalRepository;
import com.project.carsharingapp.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    @Override
    public RentalDto add(CreateRentalRequestDto requestDto) {
        Rental rental = rentalMapper.toEntity(requestDto);
        rental.setCar(getCar(requestDto.getCarId()));
        rental.setUser(getUser(requestDto.getCarId()));   // Use auth obj here
        rental.setActive(true);
        rental = rentalRepository.save(rental);
        decreaseCarInventory(requestDto.getCarId());           // add notification
        return rentalMapper.toDto(rental);
    }

    @Override
    public List<RentalDto> getByUserIdAndActiveStatus(Long userId, boolean isActive) {
        List<Rental> rentals = rentalRepository.findRentalsByUserIdAndActiveStatus(userId, isActive);
        if (rentals == null || rentals.isEmpty()) {
            throw new EntityNotFoundException("No rentals found for user id: "
                    + userId + " and active status: " + isActive);
        }
        return rentals.stream()
                .map(rentalMapper::toDto)
                .toList();
    }

// For User

//    private RentalDto getByUserIdAndActiveStatus(Auth userId, boolean isActive) {  //
//        Rental rentals = rentalRepository.findRentalByUserIdAndActiveStatus(userId, isActive);
//        if (rentals == null || rentals.isEmpty()) {
//            throw new EntityNotFoundException("No rentals found for user id: "
//                    + userId + " and active status: " + isActive);
//        }
//        return rentals.stream()
//                .map(rentalMapper::toDto)
//                .toList();
//    }

    @Override
    public RentalDto getById(Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find rental by id: " + id)
        );
        return rentalMapper.toDto(rental);
    }

    @Override
    public RentalDto setActualReturnDay(Long id) {
        return rentalRepository.findById(id)
                .map(rental -> {
                    rental.setActualReturnDate(LocalDateTime.now());
                    rentalRepository.save(rental);
                    increaseCarInventory(id);
                    return rentalMapper.toDto(rental);
                })
                .orElseThrow(() -> new EntityNotFoundException("Rental "
                        + "not found by id: " + id));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User "
                        + "not found by id: " + userId));
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

