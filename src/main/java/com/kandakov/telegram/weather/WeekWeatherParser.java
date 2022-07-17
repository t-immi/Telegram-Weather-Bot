package com.kandakov.telegram.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WeekWeatherParser {

    private final static DateTimeFormatter INPUT_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final static DateTimeFormatter OUTPUT_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("MMM-dd EE HH:mm ", Locale.US); // Locale.US переводит дату на английском

    public static List<String> convertRawDataToList(String jsonWeather) throws Exception {

        List<String> weatherList = new ArrayList<>();

        JsonNode arrNode = new ObjectMapper().readTree(jsonWeather).get("list");

        if (arrNode.isArray()) {
            for (final JsonNode objNode : arrNode) {
                String forecastTime = objNode.get("dt_txt").toString();
                if (forecastTime.contains("09:00") || forecastTime.contains("18:00")) {
                    weatherList.add(objNode.toString());
                }
            }
        }
        return weatherList;
    }

    public String parseForecastDataFromList(List<String> weatherList) {

        final StringBuilder sb = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();


        for (String line : weatherList) {

            JsonNode mainNode;
            JsonNode weatherArrNode;

            try {
                mainNode = objectMapper.readTree(line).get("main");
                weatherArrNode = objectMapper.readTree(line).get("weather");

                for (final JsonNode objNode : weatherArrNode) {

                    String dateTime = objectMapper.readTree(line).get("dt_txt").toString();
                    sb.append(formatForecastData(dateTime, mainNode.get("temp").asDouble(), objNode.get("main").toString()));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private static String formatForecastData(String dateTime, double temperature, String description) {
        LocalDateTime forecastDateTime = LocalDateTime.parse(dateTime.replaceAll("\"", ""), INPUT_DATE_TIME_FORMAT);
        String formattedDateTime = forecastDateTime.format(OUTPUT_DATE_TIME_FORMAT);

        String formattedTemperature;
        long roundedTemperature = Math.round(temperature); //округление до целого
        if (roundedTemperature > 0) {
            formattedTemperature = "+" + String.valueOf(roundedTemperature);
        } else {
            formattedTemperature = String.valueOf(roundedTemperature);
        }

        StringBuilder sb= new StringBuilder(formattedTemperature);
        formattedTemperature = String.valueOf(sb.deleteCharAt(sb.length()-1));

        String formattedDescription = description.replaceAll("\"", "");

        return String.format("%s %s %s %s", formattedDateTime, formattedTemperature, formattedDescription, System.lineSeparator());
    }
}
