package com.yurist.tgbot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static com.yurist.tgbot.model.Group.UD;
import static com.yurist.tgbot.model.Group.UDD;

public class KeyboardFactory {

    public static ReplyKeyboard withDepartmentNames() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton ud = new InlineKeyboardButton();
        ud.setText(UD.getName());
        ud.setCallbackData(UD.toString());
        rowInline.add(ud);

        InlineKeyboardButton udd = new InlineKeyboardButton();
        udd.setText(UDD.getName());
        udd.setCallbackData(UDD.toString());
        rowInline.add(udd);

        rowsInline.add(rowInline);
        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }

    public static ReplyKeyboard registerUser(Integer userId) {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton accept = new InlineKeyboardButton();
        accept.setText("Зарегистрировать");
        accept.setCallbackData("register " + userId.toString());
        rowInline.add(accept);

        InlineKeyboardButton decline = new InlineKeyboardButton();
        decline.setText("Отказать");
        decline.setCallbackData("decline");
        rowInline.add(decline);

        rowsInline.add(rowInline);
        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }

    public static ReplyKeyboard withUserSettings() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton register = new InlineKeyboardButton();
        register.setText("Отправить запрос на регистрацию");
        register.setCallbackData("register");
        rowInline.add(register);

        InlineKeyboardButton deregister = new InlineKeyboardButton();
        deregister.setText("Выйти");
        deregister.setCallbackData("deregister");
        rowInline.add(deregister);

        rowsInline.add(rowInline);
        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }

    public static ReplyKeyboard withCauseforUD() {
        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow rowInLine1 = new KeyboardRow();
        rowInLine1.add("ААА, всё пропало!!");
        rowInLine1.add("ААА, всё пропало2!!");

        KeyboardRow rowInLine2 = new KeyboardRow();
        rowInLine2.add("Понять и простить");
        rowInLine2.add("Я неуиновен!");

        KeyboardRow rowInLine3 = new KeyboardRow();
        rowInLine2.add("Заполнить объяснительную");

        rows.add(rowInLine1);
        rows.add(rowInLine2);
        rows.add(rowInLine3);
        replyKeyboard.setKeyboard(rows);
        return replyKeyboard;

    }
}

