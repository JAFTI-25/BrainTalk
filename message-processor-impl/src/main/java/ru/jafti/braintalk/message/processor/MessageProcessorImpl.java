package ru.jafti.braintalk.message.processor;

import ru.jafti.braintalk.message.processor.api.MessageProcessor;
import ru.jafti.braintalk.message.processor.api.model.TalkersMessage;

public class MessageProcessorImpl implements MessageProcessor {
    @Override
    public void submit(TalkersMessage talkersMessage) {
        System.out.println("Submit message " + talkersMessage);
    }
}
