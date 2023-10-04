package com.project.carsharingapp.telegram;

import com.project.carsharingapp.config.TelegramBotConfig;
import com.project.carsharingapp.model.Car;
import com.project.carsharingapp.model.Rental;
import com.project.carsharingapp.model.User;
import com.project.carsharingapp.repository.UserRepository;
import com.project.carsharingapp.repository.rentals.RentalRepository;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private static final String HELP_MESSAGE = """ 
            You can use menu or buttons to use this bot.
            Here is a list of available features. \n
            1. /start or Start button: Getting started with the bot. \n
            2. /login_in or Login In: To log in. If you are not signed in,
            you will not be able to receive notifications. \n
            3. /my_current_rental or Current Rental: "
            Displays  current car rental and rental details. \n
            4. /all_rental or All Rental:
            Displays the history of all car rentals and details about them. \n
            5. /exit or Exit: Sign out. After this action, "
            you will no longer be able to receive notifications until you sign in again \n""";

    private final TelegramBotConfig config;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    @PostConstruct
    private void init() {
        List<BotCommand> botCommands = new ArrayList<>();
        botCommands.add(new BotCommand("/start", "Start working with the bot"));
        botCommands.add(new BotCommand("/login_in", "Sign in"));
        botCommands.add(new BotCommand("/my_current_rental", "Show current rental"));
        botCommands.add(new BotCommand("/all_rental", "Show list of all rentals"));
        botCommands.add(new BotCommand("/help", "Instructions for use"));
        botCommands.add(new BotCommand("/exit", "Sign out"));

        try {
            execute(new SetMyCommands(botCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can`t create a bot menu", e);
        }
    }

    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            switch (messageText) {
                case "/start" :
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/login_in":
                case "Login In":
                    loginCommandReceived(chatId);
                    break;
                case "/my_current_rental":
                case "Current Rental":
                    currentRentalCommandReceived(chatId);
                    break;
                case "/all_rental":
                case "All Rentals":
                    allRentalsCommandReceived(chatId);
                    break;
                case "/exit":
                case "Exit":
                    exitRentalsCommandReceived(chatId);
                    break;
                case "/help":
                case "Help":
                    helpRentalsCommandReceived(chatId);
                    break;
                default:
                    saveUserChatId(chatId, update.getMessage().getText());
                    break;
            }
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

    public void sendNotification(Long userChatId, String message) {
        sendMessage(userChatId, message, sendButtons());
    }

    private void startCommandReceived(Long chatId, String firstName) {
        String answer = "Hi, " + firstName + """
                , nice to meet you! \n
                Welcome to the Car-Sharing-Bot. \n
                This bot was created to make it easier
                 for you to work with the car rental service."
                """;
        sendMessage(chatId, answer, sendRegisterButtons());
    }

    private void exitRentalsCommandReceived(Long chatId) {
        User user = userRepository.findByTelegramChatId(chatId);
        user.setTelegramChatId(null);
        userRepository.save(user);
        String message = "You are logged out. You will no longer receive notifications."
                + " To receive notifications again, log in again.";
        sendMessage(chatId, message, sendRegisterButtons());
    }

    private void allRentalsCommandReceived(Long chatId) {
        Long userId = userRepository.findByTelegramChatId(chatId).getTelegramChatId();
        List<Rental> rentalList = rentalRepository.findRentalsByUserIdAndActiveStatus(userId, false);
        String message = getRentalMessage(chatId, rentalList);
        sendMessage(chatId, message, sendButtons());
    }

    private void currentRentalCommandReceived(Long chatId) {
        Long userId = userRepository.findByTelegramChatId(chatId).getTelegramChatId();
        List<Rental> rentalList = rentalRepository.findRentalsByUserIdAndActiveStatus(userId, true);
        String message = getRentalMessage(chatId, rentalList);
        sendMessage(chatId, message, sendButtons());
    }

    private void helpRentalsCommandReceived(Long chatId) {
        sendMessage(chatId, HELP_MESSAGE, sendRegisterButtons());
    }

    private void loginCommandReceived(Long chatId) {
        String message = "You need to enter your login to log in. \n"
                + "An example of a login: sample@sample.com";
        sendMessage(chatId, message, sendButtons());
    }

    private void saveUserChatId(Long chatId, String text) {
        Optional<User> optionalUser = userRepository.findByEmail(text);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setTelegramChatId(chatId);
            userRepository.save(user);
            sendMessage(chatId, "Login successfully", sendButtons());
        } else {
            sendMessage(chatId, "Сan`t find user by Login. Please try again.", sendButtons());
        }
    }


    private void sendMessage(Long chatId, String textMessage, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(textMessage);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private String getRentalMessage(Long chatId, List<Rental> rentalList) {
        StringBuilder message = new StringBuilder();
        if (rentalList.isEmpty()) {
            sendMessage(chatId, "There are no rents", sendButtons());
        }
        for (Rental rental : rentalList) {
            Car car = rental.getCar();
            message.append("Rental date: ")
                    .append(rental.getRentalDate())
                    .append("\n Return date: ")
                    .append(rental.getReturnDate())
                    .append("\n Car: ")
                    .append(car.getBrand())
                    .append(" ")
                    .append(car.getCarType())
                    .append("\n");
        }
        return message.toString();
    }

    private ReplyKeyboardMarkup sendButtons() {
        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add("Current Rental");
        firstRow.add("All Rentals");

        KeyboardRow secondRow = new KeyboardRow();
        secondRow.add("Exit");
        secondRow.add("Help");

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(firstRow);
        keyboardRows.add(secondRow);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }

    private ReplyKeyboardMarkup sendRegisterButtons() {
        ReplyKeyboardMarkup keyboardMarkup = sendButtons();
        keyboardMarkup.getKeyboard().get(0).add(0, "Login In");
        return keyboardMarkup;
    }
}
