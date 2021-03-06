package com.yurist.tgbot.bot.ability;

import com.yurist.tgbot.bot.KeyboardFactory;
import com.yurist.tgbot.bot.handler.ResponseHandler;
import com.yurist.tgbot.model.Group;
import com.yurist.tgbot.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.function.Consumer;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;
import static org.telegram.abilitybots.api.util.AbilityUtils.getUser;

@Component
public class UserAbilities implements AbilityExtension {

    private final AbilityBot bot;

    private final ResponseHandler responseHandler;

    @Autowired
    private SubscriptionService subscriptionService;

    public UserAbilities(AbilityBot bot, ResponseHandler responseHandler) {
        this.bot = bot;
        this.responseHandler = responseHandler;
    }

    public Ability checkNewUser() {
        return Ability.builder()
                .name("default")
                .privacy(PUBLIC)
                .locality(ALL)
                .input(0)
                .action(ctx -> {
                    try {
                        bot.sender().execute(SendMessage.builder()
                                .text("Выберите действие")
                                .chatId(ctx.chatId().toString())
                                .replyMarkup(KeyboardFactory.withUserSettings())
                                .build()
                        );
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                })
                .build();
    }

    public Ability replyToStart() {
        return Ability.builder()
                .name("start")
                .privacy(PUBLIC)
                .locality(ALL)
                .input(0)
                .action(ctx -> responseHandler.replyToStart(ctx.chatId()))
                .build();
    }

    public Reply replyToRegister() {
        Consumer<Update> action = upd -> {
            switch (upd.getCallbackQuery().getData()) {
                case "register":
                    register(getChatId(upd), getUser(upd));
                    break;
                case "deregister":
                    deregister(getChatId(upd), getUser(upd));
            }

        };
        return Reply.of(action, Flag.CALLBACK_QUERY);
    }


    private void register(Long chatId, User user) {
        subscriptionService.sendByList(Group.ADMINS, "Зарегистрировать?", KeyboardFactory.registerUser(user.getId()));
    }

    private void deregister(Long chatId, User user) {
        subscriptionService.sendByList(Group.ADMINS, "Кикнуть?", KeyboardFactory.registerUser(user.getId()));
    }

}
