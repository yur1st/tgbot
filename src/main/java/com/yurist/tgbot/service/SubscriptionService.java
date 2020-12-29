package com.yurist.tgbot.service;

import com.yurist.tgbot.bot.BotProperties;
import com.yurist.tgbot.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.telegram.abilitybots.api.bot.BaseAbilityBot.ADMINS;

@Service
public class SubscriptionService {

    private final DBContext db;

    private final AbilityBot bot;

    public static final String SUBSCRIPTIONS = "SUBSCRIPTIONS";

    @Autowired
    private BotProperties botProperties;

    public SubscriptionService(AbilityBot bot) {
        this.db = bot.db();
        this.bot = bot;
    }

    @PostConstruct
    public void init() {
        db.getSet(ADMINS).forEach(admin -> addReceiver(Group.ADMINS, (Integer) admin));
    }

    public Map<Group, Set<Integer>> getSubscriptions() {
        return db.getMap(SUBSCRIPTIONS);
    }

    public void addReceiver(Group group, Integer userId) {
        Map<Group, Set<Integer>> map = getSubscriptions();
        Set<Integer> set = map.computeIfAbsent(group, k -> new HashSet<>());
        set.add(userId);
        map.put(group, set);
    }

    public void removeReceiver(Group group, Integer userId) {
        getSubscriptions().get(group).remove(userId);
    }

    public void sendByList(Group group, String message, Long chatId) {

    }

    public void sendByList(Group group, String message, ReplyKeyboard replyKeyboard) {
        Set<Integer> recipients = getSubscriptions().get(group);
        recipients.forEach(user -> {
            try {
                bot.sender().execute(SendMessage.builder()
                        .text(message)
                        .chatId(String.valueOf(user))
                        .replyMarkup(replyKeyboard)
                        .build()
                );
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });
    }

}
