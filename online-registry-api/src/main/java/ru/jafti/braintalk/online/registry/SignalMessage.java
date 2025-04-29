package ru.jafti.braintalk.online.registry;

import java.util.UUID;

public record SignalMessage(
        To to,
        String signalText
) {
    public record To(UUID talkerGuid) {
    }

    public static SignalMessage buildFrom(UUID talkerGuid, String signalText) {
        return new SignalMessage(new To(talkerGuid), signalText);
    }
}
