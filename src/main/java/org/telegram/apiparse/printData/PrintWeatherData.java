package org.telegram.apiparse.printData;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
}
