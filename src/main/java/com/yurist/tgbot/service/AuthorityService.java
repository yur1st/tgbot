package com.yurist.tgbot.service;

import com.yurist.tgbot.model.Authority;
import com.yurist.tgbot.model.Group;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.db.DBContext;

import java.util.Map;

@Component
public class AuthorityService {

    public final DBContext db;

    public static final String GRANTED_AUTHORITIES = "AUTHORITIES";

    public AuthorityService(AbilityBot bot) {
        this.db = bot.db();
    }

    public Map<Integer, Authority> authorities() {
        return db.getMap(GRANTED_AUTHORITIES);
    }

    public void registerUser(Integer userId) {
        Authority authority = (Authority) db.getMap(GRANTED_AUTHORITIES).getOrDefault(userId, new Authority());
        authority.setRegistered(true);
        db.getMap(GRANTED_AUTHORITIES).put(userId, authority);
    }

    public void addDepartment(Integer userId, Group group) {
        Authority authority = (Authority) db.getMap(GRANTED_AUTHORITIES).get(userId);
        authority.getDepartments().add(group);
        db.getMap(GRANTED_AUTHORITIES).put(userId, authority);
    }


}
