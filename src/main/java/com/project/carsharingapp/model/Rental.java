package com.project.carsharingapp.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE rentals SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted=false")
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "rental_date", nullable = false)
    private LocalDateTime rentalDate;
    @Column(name = "return_date", nullable = false)
    private LocalDateTime returnDate;
    @Column(name = "actual_return_date")
    private LocalDateTime actualReturnDate;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "is_deleted",nullable = false)
    private boolean isDeleted;
}
