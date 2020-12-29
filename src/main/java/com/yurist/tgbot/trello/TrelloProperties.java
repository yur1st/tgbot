package com.yurist.tgbot.trello;

import com.julienvey.trello.impl.TrelloImpl;
import com.julienvey.trello.impl.http.RestTemplateHttpClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "owen.trello")
public class TrelloProperties {

    private String trelloAccessToken;

    private String trelloKey;

    private Map<String, String> boardId;
    private Map<String, String> listId;

    @Bean
    public TrelloImpl trello() {
        return new TrelloImpl(trelloKey, trelloAccessToken, new RestTemplateHttpClient());
    }
}
