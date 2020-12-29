package com.yurist.tgbot;

import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.util.AbilityExtension;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

@Component
public class AdminAbilities implements AbilityExtension {

    private final AbilityBot bot;

    public AdminAbilities(AbilityBot bot) {
        this.bot = bot;
    }

    public Ability callUd() {

        return Ability.builder()
                .name("udxs")
                .privacy(PUBLIC)
                .locality(ALL)
                .enableStats()
                .action(ctx -> bot.silent().send("UD Vpered!", ctx.chatId())
                ).post(ctx -> {
                    bot.silent().send("Task eated", ctx.chatId());
                }).build();
    }

}
