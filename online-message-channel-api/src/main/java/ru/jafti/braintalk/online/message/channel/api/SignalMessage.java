package ru.jafti.braintalk.online.message.channel.api;

import java.util.UUID;

public record SignalMessage(
        To to,
        String signalText
) {
    public record To(UUID talkerGuid) {
    }
}
