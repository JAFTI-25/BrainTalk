package ru.jafti.braintalk.server;

import java.util.UUID;

public interface TalkerProfileService {
    UUID findByNickname(String nickname);
    String findById(UUID id);
    UUID createWithNickname(String nickname);
}

