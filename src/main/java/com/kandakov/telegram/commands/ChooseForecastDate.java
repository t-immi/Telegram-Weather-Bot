package com.kandakov.telegram.commands;

import com.kandakov.telegram.bot.Bot;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class ChooseForecastDate extends ServiceCommand {

    public ChooseForecastDate(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        Bot.chooseForecastDay = true;
        Bot.settingCity = false;

        String userName = (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());

        //обращаемся к методу суперкласса для отправки пользователю ответа
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "что бы выбрать день прогноза напишите его по типу mm-dd (05-07)");
    }
}
