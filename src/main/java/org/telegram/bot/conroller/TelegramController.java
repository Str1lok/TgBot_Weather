package org.telegram.bot.conroller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.apiparse.ApiParse;
import org.telegram.service.printData.PrintWeatherData;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Component
public class TelegramController extends TelegramLongPollingBot {
    private final String BOT_USERNAME = "Weather_mb_test_bot";
    @Value("${ACTIVE_TOKEN}")
    private String BOT_TOKEN;
    ApiParse apiParse = new ApiParse();
    PrintWeatherData print = new PrintWeatherData();
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasLocation()) {
            Location location = update.getMessage().getLocation();
            try {

                String forecast = apiParse.getDailyForecast(location.getLatitude(), location.getLongitude());
                SendMessage message_response = print.sendWeatherResponse(update.getMessage().getFrom().getId(), forecast);
                execute(message_response);
            } catch (IOException | TelegramApiException e) {
                e.printStackTrace();
                print.sendErrorMessage(update.getMessage().getChatId());
            }
        }
    }


    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

}
