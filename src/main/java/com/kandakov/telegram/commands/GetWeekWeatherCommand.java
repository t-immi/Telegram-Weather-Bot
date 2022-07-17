package com.kandakov.telegram.commands;

import com.kandakov.telegram.bot.Bot;
import com.kandakov.telegram.weather.GetWeather;
import com.kandakov.telegram.weather.WeekWeatherParser;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class GetWeekWeatherCommand extends ServiceCommand{

    public GetWeekWeatherCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String city = Bot.getUserCitySettings().get(chat.getId());
        String answer;

        try {
            String jsonWeather = new GetWeather().getWeatherJson(city);
            String weather = new WeekWeatherParser().parseForecastDataFromList(WeekWeatherParser.convertRawDataToList(jsonWeather));
            answer = city + ":\n" + weather;
        } catch (Exception e) {
            e.printStackTrace();
            answer = "что-то пошло не так, возможно вы забыли указать город или сделали это неправильно\nчтобы узнать как это сделать напишите /help";
        }

        String userName = (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());

        //обращаемся к методу суперкласса для отправки пользователю ответа
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, answer);
    }
}
