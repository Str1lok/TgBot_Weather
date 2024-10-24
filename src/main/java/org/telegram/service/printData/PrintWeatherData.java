package org.telegram.service.printData;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class PrintWeatherData {

    public String formatForecastData(JsonObject forecastData) {
        StringBuilder forecast = new StringBuilder();
        JsonObject hourlyData = forecastData.getAsJsonObject("hourly");
        JsonArray temperatureArray = hourlyData.getAsJsonArray("temperature_2m");

        if (temperatureArray != null) {
            for (int i = 0; i < temperatureArray.size(); i++) {
                float temp = temperatureArray.get(i).getAsFloat();
                String temp_string = Float.toString(temp);
                forecast.append(temp_string).append("°C, ").append("\n");
            }
        }
        return "Прогноз на сутки:\n" + forecast;
    }
    public SendMessage sendWeatherResponse(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        System.out.println(sendMessage);
        return sendMessage;
    }
    public void sendErrorMessage(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Ошибка получения данных о погоде. Попробуйте позже.");
        System.out.println(sendMessage);
    }
}
