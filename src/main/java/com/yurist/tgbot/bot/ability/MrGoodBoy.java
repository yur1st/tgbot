package com.yurist.tgbot.bot.ability;

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Card;
import com.yurist.tgbot.bot.KeyboardFactory;
import com.yurist.tgbot.trello.TrelloProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.validation.constraints.NotNull;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.yurist.tgbot.model.Group.valueOf;
import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

@Component
public class MrGoodBoy implements AbilityExtension {

    private final AbilityBot bot;

    @Autowired
    private Trello trello;

    @Autowired
    private TrelloProperties trelloProperties;

    public MrGoodBoy(AbilityBot bot) {
        this.bot = bot;
    }

    public Ability callUd() {

        return Ability.builder()
                .name("ud")
                .privacy(PUBLIC)
                .locality(ALL)
                .enableStats()
                .action(ctx -> bot.silent().send("UD Vpered!", ctx.chatId())
                ).post(ctx -> {
                    Card card = new Card();
                    card.setName("Test card for UD");
                    card.setDesc("O-la-la");
                    Card createdCard = trello.createCard(trelloProperties.getListId().get("ud"), card);
                    bot.silent().send("Task \"" + createdCard.getName() + "\" created", ctx.chatId());
                }).build();
    }

    public Ability callUdd() {

        return Ability.builder()
                .name("udd")
                .privacy(PUBLIC)
                .locality(ALL)
                .enableStats()
                .action(ctx -> bot.silent().send("Yahoo", ctx.chatId())
                ).post(ctx -> {
                    Card card = new Card();
                    card.setName("Test card for UDD");
                    card.setDesc("O-la-la");
                    Card createdCard = trello.createCard(trelloProperties.getListId().get("udd"), card);
                    bot.silent().send("Task \"" + createdCard.getName() + "\" created", ctx.chatId());
                }).build();
    }


    public Ability firstReply() {
        return Ability.builder()
                .name("call")
                .privacy(PUBLIC)
                .locality(ALL)
                .enableStats()
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


    public Ability causeReply() {
        return Ability.builder()
                .name("callud")
                .privacy(PUBLIC)
                .locality(ALL)
                .enableStats()
                .action(ctx -> {
                    try {
                        bot.sender().execute(SendMessage.builder()
                                .text("Please choose Query")
                                .chatId(ctx.chatId().toString())
                                .replyMarkup(KeyboardFactory.withCauseforUD())
                                .build()
                        );
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                })
                .build();
    }

    public ReplyFlow directionFlow() {
        return ReplyFlow.builder(bot.db())
                .action(upd -> bot.silent().send("Ну, я жду!", getChatId(upd)))
                .onlyIf(hasMessageWith("Заполнить объяснительную"))
                .next(Reply.of(upd ->
                                bot.silent().send("Ну ладно.", getChatId(upd)),
                        hasMessageWith("простить")))
                .next(Reply.of(upd ->
                                bot.silent().send("Перепиши заново", getChatId(upd)),
                        hasMessageWith("неуиновен")))
                .build();
    }


    public Reply replyToButtons() {
        Consumer<Update> action = upd -> createTask(getChatId(upd), upd.getCallbackQuery().getData());
        return Reply.of(action, Flag.CALLBACK_QUERY);
    }

    private void createTask(Long chatId, String buttonId) {
        Card card = new Card();
        card.setName("Test card for " + valueOf(buttonId).getName());
        card.setDesc("O-la-la");
        Card createdCard = trello.createCard(trelloProperties.getListId().get(buttonId), card);
        bot.silent().send("Task \"" + createdCard.getName() + "\" created", chatId);
    }

    @NotNull
    private Predicate<Update> hasMessageWith(String msg) {
        return upd -> upd.getMessage().getText().contains(msg);
    }

}
