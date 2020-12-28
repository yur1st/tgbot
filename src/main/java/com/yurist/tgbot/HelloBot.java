package com.yurist.tgbot;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Consumer;

import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class HelloBot extends AbilityBot {

    private final BotProperties conf;

    public HelloBot(BotProperties conf) {
        super(conf.getToken(), conf.getBotName());
        this.conf = conf;
    }

    @Override
    public int creatorId() {
        return conf.getCreatorId();
    }

    public Ability sayHelloWorld() {
        return Ability.builder()
                .name("hello")
                .info("Says Hello World!")
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> silent.send("Hello world!", ctx.chatId()))
                .build();
    }

    public Reply sayYuckOnImage() {
        Consumer<Update> action = upd -> silent.send("Yuck", getChatId(upd));
        return Reply.of(action, Flag.PHOTO);
    }
}
