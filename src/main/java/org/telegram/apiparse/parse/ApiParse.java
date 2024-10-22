package org.telegram.apiparse.parse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.telegram.apiparse.printData.PrintWeatherData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiParse {
    PrintWeatherData pwd = new PrintWeatherData();
    private final String apiKey = "a64ea4780f14fda8a8a29fa9fec1b259";
    private final String apiUrl = "https://api.open-meteo.com/v1/forecast?"; // "https://api.openweathermap.org/data/2.5/weather?";
    public String getApiResponse(String url) throws IOException {
        URL apiURL = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } else {
            return null;
        }
    }
    public String getDailyForecast(double latitude, double longitude) throws IOException {
        String url = buildForecastUrl(latitude, longitude);
        String jsonResponse = getApiResponse(url);
        JsonObject forecastData = parseJson(jsonResponse);

        if (forecastData != null) {
            return pwd.formatForecastData(forecastData);
        } else {
            return "Ошибка получения данных о прогнозе.";
        }
    }
    private String buildForecastUrl(double latitude, double longitude) {
        return apiUrl +
                "latitude=" + latitude +
                "&longitude=" + longitude + "&hourly=temperature_2m&timezone=auto&forecast_days=1";
                // "&appid=" + apiKey +
                // "&units=metric";
    }
    public JsonObject parseJson(String jsonResponse) {
        if (jsonResponse != null) {
            Gson gson = new Gson();
            return gson.fromJson(jsonResponse, JsonObject.class);
        }
        return null;
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
