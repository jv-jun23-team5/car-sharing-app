package com.project.carsharingapp.service.impl;

import com.project.carsharingapp.service.NotificationService;
import com.project.carsharingapp.telegram.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final TelegramBot telegramBot;

    @Override
    public void sendMessage(Long userChatId, String message) {
        telegramBot.sendNotification(userChatId, message);
    }
}
