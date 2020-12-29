package com.yurist.tgbot.model;

import lombok.Getter;

@Getter
public enum Group {
    UD("УД"),
    UDD("УДД"),
    UTP("УТП"),
    UPM("УПМ"),
    UIT("УИТ"),
    ADMINS("Администраторы"),
    ENGINEERS("Технологи"),
    ALL("Все");

    private String name;

    Group(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name();
    }
}
