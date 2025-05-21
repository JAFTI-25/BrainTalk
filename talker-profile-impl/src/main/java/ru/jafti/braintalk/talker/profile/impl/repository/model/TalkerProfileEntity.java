package ru.jafti.braintalk.talker.profile.impl.repository.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

public class TalkerProfileEntity {
        @Id
        private UUID id;
        private String nickname;

        public UUID getTalkerId() {
            return id;
        }
        public void setTalkerId(UUID messageId) {
            this.id = messageId;
        }
        public String getFromTalker() {
            return nickname;
        }
        public void setTalkerNickname(String nickname) {
            this.nickname = nickname;
        }

}
