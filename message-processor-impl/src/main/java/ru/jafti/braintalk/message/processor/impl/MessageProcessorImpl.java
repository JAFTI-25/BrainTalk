package ru.jafti.braintalk.message.processor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.jafti.braintalk.message.processor.api.MessageProcessor;
import ru.jafti.braintalk.message.processor.api.model.TalkersMessage;
import ru.jafti.braintalk.message.storage.api.model.StorableMessage;
import ru.jafti.braintalk.online.message.channel.api.OnlineMessageChannel;
import ru.jafti.braintalk.online.message.channel.api.OutgoingMessage;
import ru.jafti.braintalk.online.message.channel.api.SignalMessage;
import ru.jafti.braintalk.talker.profile.api.TalkerProfileService;
import ru.jafti.braintalk.message.storage.api.MessageStorage;

import java.util.UUID;

@Component
public class MessageProcessorImpl implements MessageProcessor {

    private static final Logger log = LoggerFactory.getLogger(MessageProcessorImpl.class);

    private final OnlineMessageChannel messageChannel;
    private final TalkerProfileService talkerProfileService;
    private final MessageStorage messageStorage;

    public MessageProcessorImpl(
            OnlineMessageChannel messageChannel,
            TalkerProfileService talkerProfileService,
            MessageStorage messageStorage
    ) {
        this.messageChannel = messageChannel;
        this.talkerProfileService = talkerProfileService;
        this.messageStorage = messageStorage;
    }

    @Override
    public void submit(TalkersMessage talkersMessage) {
        log.trace("Submit message {}", talkersMessage);

        //1. Проверить что пользователь, которому пересылается сообщение, существует
        String toNickname = talkersMessage.to().nickname();
        UUID toTalkerGuid = talkerProfileService.findByNickname(toNickname);
        if (toTalkerGuid == null) {
            log.warn("Talker not found by nickname {}", toNickname);
            signal(talkersMessage.from().talkerGuid(), "Talker " + toNickname + " not registered");
            return;
        }

        String messageId = talkersMessage.messageId();
        sendMessage(talkersMessage, toTalkerGuid, messageId);
        storeMessage(talkersMessage, messageId, toTalkerGuid);
    }

    private void signal(UUID talkerGuid, String message) {
        messageChannel.signal(new SignalMessage(new SignalMessage.To(talkerGuid), message));
    }

    private void sendMessage(TalkersMessage talkersMessage, UUID toTalkerGuid, String messageId) {
        if (!messageChannel.isOnline(toTalkerGuid)) {
            return;
        }

        var outgoingMessage = OutgoingMessage.buildFrom(
                messageId,
                talkersMessage.from().nickname(),
                toTalkerGuid,
                talkersMessage.content().rawContent()
        );

        messageChannel.send(outgoingMessage);
    }

    private void storeMessage(TalkersMessage talkersMessage, String messageId, UUID toTalkerGuid) {
        var message = StorableMessage.buildFrom(
                messageId,
                talkersMessage.from().nickname(),
                talkersMessage.from().talkerGuid(),
                talkersMessage.to().nickname(),
                toTalkerGuid,
                talkersMessage.content().rawContent()
        );

        messageStorage.store(message);
    }
}
