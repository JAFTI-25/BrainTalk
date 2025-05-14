package ru.jafti.braintalk.server.api;

import java.util.UUID;

public interface RegisterApi {

    RegisterResponse register(RegisterRequest request);

    record RegisterRequest(String talkerNickname) {}
    record RegisterResponse(UUID talkerGuid) {}
}
