package com.yurist.tgbot;

import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.validation.constraints.NotNull;
import java.util.function.Predicate;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

@Component
public class ReplyFlowBot extends AbilityBot {

    private final BotProperties conf;

    public ReplyFlowBot(BotProperties conf) {
        super(conf.getToken(), conf.getBotName());
        this.conf = conf;
    }

    @Override
    public int creatorId() {
        return conf.getCreatorId();
    }

    public ReplyFlow directionFlow() {
        return ReplyFlow.builder(db)
                .action(upd -> silent.send("Command me to go left or right!", getChatId(upd)))
                .onlyIf(hasMessageWith("wake up"))
                .next(Reply.of(upd ->
                                silent.send("Sir, I have gone left.", getChatId(upd)),
                        hasMessageWith("left")))
                .next(Reply.of(upd ->
                                silent.send("Sir, I have gone right.", getChatId(upd)),
                        hasMessageWith("right")))
                .build();
    }

    @NotNull
    private Predicate<Update> hasMessageWith(String msg) {
        return upd -> upd.getMessage().getText().equalsIgnoreCase(msg);
    }

    public Ability nice()  {
        return Ability.builder()
                .name("nice")
                .privacy(PUBLIC)
                .locality(ALL)
                .action(ctx -> silent.send("You're wonderful!", ctx.chatId())
                ).build();
    }

    public Ability notNice() {
        return Ability.builder()
                .name("notnice")
                .privacy(PUBLIC)
                .locality(ALL)
                .action(ctx -> silent().send("You're horrible!", ctx.chatId())
                ).build();
    }
}
