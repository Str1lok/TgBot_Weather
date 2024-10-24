package org.telegram.apiparse;

import com.google.gson.JsonObject;
import org.telegram.apiparse.parse.ParseJson;
import org.telegram.service.printData.PrintWeatherData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiParse {
    PrintWeatherData pwd = new PrintWeatherData();
    ParseJson parseJson = new ParseJson();
    public final String apiUrl = "https://api.open-meteo.com/v1/forecast?"; // "https://api.openweathermap.org/data/2.5/weather?";
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
        JsonObject forecastData = parseJson.parseJson(jsonResponse);

        if (forecastData != null) {
            return pwd.formatForecastData(forecastData);
        } else {
            return "Ошибка получения данных о прогнозе.";
        }
    }
    public String buildForecastUrl(double latitude, double longitude) {
        return apiUrl +
                "latitude=" + latitude +
                "&longitude=" + longitude + "&hourly=temperature_2m&timezone=auto&forecast_days=1";
    }
}
