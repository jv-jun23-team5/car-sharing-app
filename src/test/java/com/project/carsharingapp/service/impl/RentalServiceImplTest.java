package com.project.carsharingapp.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class RentalServiceImplTest {
    @Mock
    private RentalRepository rentalRepository;
    @Mock
    private CarRepository carRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private RentalServiceImpl rentalService;
    @Mock
    private Authentication authentication;
    @Mock
    private RentalMapper rentalMapper;
    private User user;
    private Car car;
    private Rental rental;
    private CreateRentalRequestDto requestDto;
    private RentalDto expectedRentalDto;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        car = new Car();
        car.setId(1L);
        car.setModel("Sample Model");
        car.setBrand("Sample Brand");
        car.setInventory(10);
        car.setCarType(Car.CarType.SEDAN);
        car.setDailyFee(new BigDecimal("50.00"));

        rental = new Rental();
        rental.setId(1L);
        rental.setRentalDate(
                LocalDateTime.of(2023, 10, 5, 9, 0));
        rental.setReturnDate(
                LocalDateTime.of(2023, 10, 7, 12, 0));
        rental.setCar(car);

        requestDto = new CreateRentalRequestDto();
        requestDto.setRentalDate(rental.getRentalDate());
        requestDto.setReturnDate(rental.getReturnDate());
        requestDto.setCarId(rental.getCar().getId());

        expectedRentalDto = new RentalDto();
        expectedRentalDto.setId(1L);
        expectedRentalDto.setRentalDate(
                LocalDateTime.of(2023, 10, 5, 9, 0));
        expectedRentalDto.setReturnDate(
                LocalDateTime.of(2023, 10, 7, 12, 0));
        expectedRentalDto.setActualReturnDate(null);
        expectedRentalDto.setCarId(car.getId());
        expectedRentalDto.setUserId(user.getId());
    }

    @Test
    @DisplayName("""
            Test the 'add' method to create new rental
            with valid request parameters
            """)
    void add_ValidRequestData_ReturnRentalDto() {
        when(rentalMapper.toEntity(requestDto)).thenReturn(rental);
        when(authentication.getName()).thenReturn(user.getEmail());
        when(rentalMapper.toDto(any())).thenReturn(expectedRentalDto);
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        RentalDto actualRentalDto = rentalService.add(requestDto, authentication);

        assertNotNull(actualRentalDto);
        assertEquals(expectedRentalDto, actualRentalDto);
    }

    @Test
    @DisplayName("""
            Test the 'getById' method with valid request parameters
            to retrieve a rental by ID
            """)
    void getById_ValidRentalId_ReturnRentalDto() {
        when(rentalRepository.findById(rental.getId())).thenReturn(Optional.of(rental));
        when(rentalMapper.toDto(any())).thenReturn(expectedRentalDto);

        RentalDto rentalDto = rentalService.getById(1L);

        assertNotNull(rentalDto);
    }

    @Test
    @DisplayName("""
            Test the 'getById' method with a non-existent rental ID.
            The method should throw an EntityNotFoundException.
            """)
    void getById_WithNonExistedBookId_ShouldThrowException() {
        Long rentalId = 100L;
        when(rentalRepository.findById(rentalId))
                .thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> rentalService.getById(rentalId));
        String expected = "Can't find rental by id: " + rentalId;
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Test the 'setActualReturnDay' method with valid request data.
            The method should return information depend on parameters.
            """)
    void setActualReturnDay_ValidRequestData_ReturnRentalDto() {
        when(authentication.getName()).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(carRepository.save(car)).thenReturn(car);
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        when(rentalRepository.findByUserIdAndActiveStatus(user.getId(), true))
                .thenReturn(Optional.of(rental));
        when(rentalMapper.toDto(any())).thenReturn(expectedRentalDto);

        RentalDto actualRentalDto = rentalService.setActualReturnDay(authentication);

        assertNotNull(actualRentalDto);
        assertEquals(expectedRentalDto, actualRentalDto);
    }
}
