package com.yurist.tgbot.bot;

import org.telegram.abilitybots.api.toggle.CustomToggle;


public class Toggle extends CustomToggle {

    @Override
    public CustomToggle toggle(String abilityName, String targetName) {
        return super.toggle(abilityName, targetName);
    }

    @Override
    public CustomToggle turnOff(String ability) {
        return super.turnOff(ability);
    }
}
