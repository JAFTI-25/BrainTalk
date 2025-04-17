package ru.jafti.braintalk.message.processor.api;

import ru.jafti.braintalk.message.processor.api.model.TalkersMessage;

public interface MessageProcessor {
    void submit(TalkersMessage talkersMessage);
}
