package com.yurist.tgbot.trello;

import com.julienvey.trello.impl.TrelloImpl;
import com.julienvey.trello.impl.http.RestTemplateHttpClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "owen.trello")
public class TrelloProperties {

    private String trelloAccessToken;

    private String trelloKey;
    private String udBoardId;
    private String uddBoardId;
    private String utpBoardId;
    private String uitBoardId;
    private String upmBoardId;

    private String udInboxListId;
    private String uddInboxListId;
    private String utpInboxListId;
    private String uitInboxListId;
    private String upmInboxListId;

    @Bean
    public TrelloImpl trello() {
        return new TrelloImpl(trelloKey, trelloAccessToken, new RestTemplateHttpClient());
    }
}
