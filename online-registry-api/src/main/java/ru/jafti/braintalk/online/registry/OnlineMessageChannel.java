package ru.jafti.braintalk.online.registry;

import java.util.UUID;

public interface OnlineMessageChannel {
    void signal(SignalMessage message);
    void send(OutgoingMessage message);
    boolean isOnline(UUID talkerGuid);
}
