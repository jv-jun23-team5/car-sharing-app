package com.project.carsharingapp.service;

public interface SchedulerService {
    void checkOverdueRentals();

    void checkExpiredPaymentSessions();
}
