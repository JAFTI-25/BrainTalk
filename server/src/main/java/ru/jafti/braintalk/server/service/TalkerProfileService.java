package ru.jafti.braintalk.server.service;

import java.util.UUID;

public interface TalkerProfileService {
    /// if not found returns null
    String findById(UUID id);
    UUID findByNickname(String nickname);
    UUID createWithNickname(String nickname);
}

