package ru.jafti.braintalk.talker.profile.impl.repository.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import ru.jafti.braintalk.talker.profile.impl.persist.DbInitializer;

import java.util.UUID;

@Table(DbInitializer.TABLE_NAME)
public class TalkerProfileEntity {

    @Id
    private UUID talkerId;
    private String nickname;

    public UUID getTalkerId() {
        return talkerId;
    }

    public void setTalkerId(UUID talkerId) {
        this.talkerId = talkerId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
