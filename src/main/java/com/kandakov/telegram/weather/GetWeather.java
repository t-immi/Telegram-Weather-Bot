package com.kandakov.telegram.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class GetWeather {
    public String getWeatherJson(String city) throws IllegalArgumentException{
        try {
            String API_CALL_TEMPLATE = "https://api.openweathermap.org/data/2.5/forecast?q=";
            String API_KEY_TEMPLATE = "&APPID=50781babdd66c41a93ee97cecbde605b";

            String urlString = API_CALL_TEMPLATE + city + API_KEY_TEMPLATE;
            URL urlObject = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = connection.getResponseCode();
            if (responseCode == 404) {
                throw new IllegalArgumentException();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();
            return response.toString();

        } catch (ProtocolException e) {
            return "ProtocolException";
        } catch (MalformedURLException e) {
            return "MalformedURLException";
        } catch (IOException e) {
            return "IOException";
        }
    }
}