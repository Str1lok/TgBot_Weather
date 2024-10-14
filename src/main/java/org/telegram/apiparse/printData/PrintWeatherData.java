package org.telegram.apiparse.printData;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class PrintWeatherData {

    public String formatForecastData(JsonObject forecastData) {
        StringBuilder forecast = new StringBuilder();
        JsonArray list = forecastData.getAsJsonArray("list");
        for (int i = 0; i < 8; i++) {
            JsonObject item = list.get(i).getAsJsonObject();
            JsonObject main = item.getAsJsonObject("main");
            JsonArray weatherArray = item.getAsJsonArray("weather");
            JsonObject weather = weatherArray.get(0).getAsJsonObject();
            String time = item.get("dt_txt").getAsString();
            forecast.append(time.substring(11, 16)).append(": ").append(main.get("temp").getAsDouble()).append("°C, ").append(weather.get("description").getAsString()).append("\n");
        }
        return "Прогноз на сутки:\n" + forecast;
    }
}
