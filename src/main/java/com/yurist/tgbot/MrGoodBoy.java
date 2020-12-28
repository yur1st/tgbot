package com.yurist.tgbot;

import com.julienvey.trello.domain.Card;
import com.julienvey.trello.impl.TrelloImpl;
import com.yurist.tgbot.trello.TrelloProperties;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.validation.constraints.NotNull;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

@Component
public class MrGoodBoy implements AbilityExtension {

    private final AbilityBot bot;

    private final TrelloImpl trello;

    private final TrelloProperties trelloProperties;

    public MrGoodBoy(AbilityBot bot, TrelloImpl trello, TrelloProperties config) {
        this.bot = bot;
        this.trello = trello;

        this.trelloProperties = config;
    }

    public Ability callUd() {


        return Ability.builder()
                .name("ud")
                .privacy(PUBLIC)
                .locality(ALL)
                .action(ctx -> bot.silent().send("UD Vpered!", ctx.chatId())
                ).post(ctx -> {
                    Card card = new Card();
                    card.setName("Test card for UD");
                    card.setDesc("O-la-la");
                    Card createdCard = trello.createCard(trelloProperties.getUdInboxListId(), card);
                    bot.silent().send("Task \"" + createdCard.getName() + "\" created", ctx.chatId());
                }).build();
    }

    public Ability callUdd() {

        return Ability.builder()
                .name("udd")
                .privacy(PUBLIC)
                .locality(ALL)
                .action(ctx -> bot.silent().send("Yahoo", ctx.chatId())
                ).post(ctx -> {
                    Card card = new Card();
                    card.setName("Test card for UDD");
                    card.setDesc("O-la-la");
                    Card createdCard = trello.createCard(trelloProperties.getUddInboxListId(), card);
                    bot.silent().send("Task \"" + createdCard.getName() + "\" created", ctx.chatId());
                }).build();
    }


    public Ability firstReply() {
        return Ability.builder()
                .name("call")
                .privacy(PUBLIC)
                .locality(ALL)
                .action(ctx -> {
                    try {
                        bot.sender().execute(SendMessage.builder()
                                .text("Please choose board")
                                .chatId(ctx.chatId().toString())
                                .replyMarkup(KeyboardFactory.withDepartmentNames())
                                .build()
                        );
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                })
                .build();
    }


    public Reply replyToButtons() {
        Consumer<Update> action = upd -> replyToButtons(getChatId(upd), upd.getCallbackQuery().getData());
        return Reply.of(action, Flag.CALLBACK_QUERY);
    }

    private void replyToButtons(long chatId, String buttonId) {
        switch (buttonId) {
            case "ud":
                createTask(chatId, "УД", trelloProperties.getUdInboxListId());
                break;
            case "udd":
                createTask(chatId, "УДД", trelloProperties.getUddInboxListId());
                break;
        }
    }

    private void createTask(Long chatId, String name, String listId) {
        Card card = new Card();
        card.setName("Test card for " + name);
        card.setDesc("O-la-la");
        Card createdCard = trello.createCard(listId, card);
        bot.silent().send("Task \"" + createdCard.getName() + "\" created", chatId);
    }


    @NotNull
    private Predicate<Update> hasMessageWith(String msg) {
        return upd -> upd.getCallbackQuery().getData().equals(msg);
    }


}
