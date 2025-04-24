package ru.jafti.braintalk.message.storage.api;

import ru.jafti.braintalk.message.storage.api.model.StorableMessage;

public interface MessageStorage {
    void store(StorableMessage storableMessage);
}
