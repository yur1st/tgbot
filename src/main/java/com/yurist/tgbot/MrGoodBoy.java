package com.yurist.tgbot;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.util.AbilityExtension;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;


public class MrGoodBoy implements AbilityExtension {

    private AbilityBot bot;

    public MrGoodBoy(AbilityBot bot) {
        this.bot = bot;
    }

    public Ability nice() {
        return Ability.builder()
                .name("nice")
                .privacy(PUBLIC)
                .locality(ALL)
                .action(ctx -> bot.silent().send("You're wonderful!", ctx.chatId())
                ).build();
    }
}
