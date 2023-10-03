package com.project.carsharingapp.service;

import com.project.carsharingapp.config.TelegramBotConfig;
import com.project.carsharingapp.model.User;
import com.project.carsharingapp.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class NotificationService extends TelegramLongPollingBot {
    private final TelegramBotConfig config;
    private final UserRepository userRepository;
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
            }
            if (!messageText.startsWith("/")) {
                saveUserChatId(chatId, update.getMessage().getText());
            }
        }
    }

    private void saveUserChatId(Long chatId, String text) {
        Optional<User> optionalUser = userRepository.findByEmail(text);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setTelegramChatId(chatId);
            userRepository.save(user);
            sendMessage(chatId, "Success");
        } else {
            sendMessage(chatId, "can`t find user");
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    public void sendNotification(Long userId, String message) {
        //need security
        Long chatId = 0L;
        sendMessage(chatId, message);
    }

    private void startCommandReceived(Long chatId, String firstName) {
         String answer = "Hi, " + firstName + ", nice to meet you! \n"
                 + "Enter your username to get started:";
         sendMessage(chatId, answer);

    }

    private void sendMessage(Long chatId, String textMessage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(textMessage);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
