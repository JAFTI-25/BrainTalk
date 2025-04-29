package ru.jafti.braintalk.online.registry;

import java.util.UUID;

public interface OnlineMessageChannel {
    void send(SendMessageRequest request);
    void signal(SignalMessage message);

    boolean isOnline(UUID talkerGuid);
}
