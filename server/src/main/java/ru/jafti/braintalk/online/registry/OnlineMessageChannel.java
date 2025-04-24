package ru.jafti.braintalk.online.registry;

import java.util.UUID;

public interface OnlineMessageChannel {
    void send(SendMessageRequest request);
    boolean isOnline(UUID talkerGuid);
}
