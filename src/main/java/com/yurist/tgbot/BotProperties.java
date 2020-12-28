package com.yurist.tgbot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "owen.tgbot")
public class BotProperties {
    private String botName;
    private String token;
    private int creatorId;

}
