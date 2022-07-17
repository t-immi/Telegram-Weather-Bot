package com.kandakov.telegram.bot;

import com.kandakov.telegram.weather.DayWeatherParser;
import com.kandakov.telegram.weather.GetWeather;

public class NonCommand {
    public String nonCommandExecute(Long chatId, String userName, String text) {

        try {
            if (Bot.settingCity) {
                String s = Bot.getUserCitySettings().get(chatId);
                Bot.settingCity = false;
                if (!text.equals(s)) { //если город != уже записанному под данным id, то мы его записываем
                    Bot.getUserCitySettings().put(chatId, text);
                    return userName + ", ваш город изменен на " + text + "\nпроверьте, что он написан правильно";
                } else {
                    return userName + ", этот город итак уже записан";
                }
            } else if (Bot.chooseForecastDay) {
                Bot.chooseForecastDay = false;
                try {
                    String jsonWeather = new GetWeather().getWeatherJson(Bot.getUserCitySettings().get(chatId));
                    return Bot.getUserCitySettings().get(chatId) + " " + text + " :\n" + new DayWeatherParser().parseForecastDataFromList(DayWeatherParser.convertRawDataToList(jsonWeather, text));

                } catch (IllegalArgumentException e) {
                    return userName + ", что-то пошло не так, возможно вы забыли указать город или сделали это неправильно\nчтобы узнать как это сделать напишите /help";
                }
            } else {
                return  "если вы хотите установить город напишите /setcity\nесли вы хотите посмотреть прогноз погоды за какой-то день напишите /dayforecast";
            }
        } catch (Exception e) {
            return "простите, я не понимаю вас. Возможно, Вам поможет /help";
        }
    }
}
