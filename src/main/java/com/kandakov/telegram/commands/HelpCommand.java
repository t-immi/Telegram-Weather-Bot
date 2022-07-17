package com.kandakov.telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends ServiceCommand{

    public HelpCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());

        //обращаемся к методу суперкласса для отправки пользователю ответа
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "для начала нужно установить ваш город\nдля этого сначала напишите комманду /setcity\na потом напишите его название в чат на английском\nпо типу London, Rostov-on-Don\nдалее чтобы посмотреть прогноз погоды на неделю напишите:\n/weekforecast\nчтобы посмотреть прогноз погоды на какой-то конкретный день напишите:\n/dayforecast");
    }
}