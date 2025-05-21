package ru.jafti.braintalk.talker.profile.api;

import java.util.UUID;

public interface TalkerProfileStorage {

    String findById(UUID talkerId);
    UUID findByNickname(String nickname);
    UUID createWithNickname(String nickname);

}
