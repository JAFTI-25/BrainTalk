package ru.jafti.braintalk.server;

import java.util.UUID;

public interface TalkerProfileService {
    UUID findByNickname(String nickname);
    UUID createWithNickname(String nickname);
}

