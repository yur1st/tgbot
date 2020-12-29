package com.yurist.tgbot.model;

import lombok.Data;

import java.util.List;

@Data
public class Authority {

    private List<Enum<Group>> departments;
    private boolean isRegistered;

}
