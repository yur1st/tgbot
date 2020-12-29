package com.yurist.tgbot.service;

import com.yurist.tgbot.model.Group;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SubscriptionService {

    private final DBContext db;

    private final AbilityBot bot;

    public static final String SUBSCRIPTIONS = "SUBSCRIPTIONS";

    public SubscriptionService(AbilityBot bot) {
        this.db = bot.db();
        this.bot = bot;
    }

    public Map<Group, List<Long>> getSubscriptions() {
        return db.getMap(SUBSCRIPTIONS);
    }

    public void addReceiver(Group group, Long userId) {
        getSubscriptions().getOrDefault(group, new ArrayList<>()).add(userId);
    }

    public void removeReceiver(Group group, Long userId) {
        getSubscriptions().get(group).remove(userId);
    }

    public void sendByList(Group group, String message, Long chatId) {

    }

    public void sendByList(Group group, String message, Long chatId, ReplyKeyboard replyKeyboard) {
        List<Long> recipients = getSubscriptions().get(group);
        recipients.forEach(user -> {
            try {
                bot.sender().execute(SendMessage.builder()
                        .text(message)
                        .chatId(String.valueOf(chatId))
                        .replyMarkup(replyKeyboard)
                        .build()
                );
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });
    }

}
