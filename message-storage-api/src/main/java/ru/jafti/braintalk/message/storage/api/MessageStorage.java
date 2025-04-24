package ru.jafti.braintalk.message.storage.api;

import ru.jafti.braintalk.message.processor.api.model.TalkersMessage;

public interface MessageStorage {
    void store(TalkersMessage talkerMessage);
}
