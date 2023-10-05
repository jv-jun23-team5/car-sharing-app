package com.project.carsharingapp.service.impl;

import com.project.carsharingapp.service.SchedulerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerServiceImp implements SchedulerService {
    @Override
    public void checkOverdueRentals() {
        System.out.println("SCHEDULED TELEGRAM");
    }

    @Override
    @Scheduled(cron = "*/1 * * * *" )
    public void checkExpiredPaymentSessions() {
        System.out.println("SCHEDULED PAYMENT");
    }
}
