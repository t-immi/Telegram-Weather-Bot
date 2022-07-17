package com.kandakov.telegram;

import com.kandakov.telegram.bot.Bot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Map;

public class App {


    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot("@WeatherJavaTelegramBot", "5424194065:AAH8-VogEpbHi-COtLp5yFusGl9ZqyN5yiY"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

