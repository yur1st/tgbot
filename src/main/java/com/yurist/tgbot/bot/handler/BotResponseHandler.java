package com.yurist.tgbot.bot.handler;

import com.yurist.tgbot.bot.Constants;
import com.yurist.tgbot.bot.KeyboardFactory;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

@Component
public class BotResponseHandler implements ResponseHandler {

    private final AbilityBot bot;

    private final MessageSender sender;
    private final Map<Long, State> chatStates;

    public BotResponseHandler(AbilityBot bot) {
        this.bot = bot;
        this.sender = bot.sender();
        chatStates = bot.db().getMap(Constants.CHAT_STATES);
    }

    @Override
    public void replyToStart(Long chatId) {
        try {
            sender.execute(SendMessage.builder()
                    .text(Constants.START_REPLY)
                    .chatId(String.valueOf(chatId))
                    .replyMarkup(KeyboardFactory.withUserSettings())
                    .build());
            chatStates.put(chatId, State.AWAITING_ANSWER);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
