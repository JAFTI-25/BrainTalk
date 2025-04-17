package ru.jafti.braintalk.talker.profile.api;

import java.util.UUID;

public interface TalkerProfileService {
    String findById(UUID id);
    UUID findByNickname(String nickname);
    UUID createWithNickname(String nickname);
}

