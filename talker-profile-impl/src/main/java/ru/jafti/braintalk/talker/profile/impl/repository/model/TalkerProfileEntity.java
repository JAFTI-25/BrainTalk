package ru.jafti.braintalk.talker.profile.impl.repository.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import ru.jafti.braintalk.talker.profile.impl.persist.DbInitializer;

import java.util.UUID;

@Table(DbInitializer.TABLE_NAME)
public class TalkerProfileEntity {

    @Id
    private UUID id;
    private String nickname;

    public UUID id() {
        return id;
    }

    public String nickname() {
        return nickname;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setNickName(String nickname) {
        this.nickname = nickname;
    }
}
