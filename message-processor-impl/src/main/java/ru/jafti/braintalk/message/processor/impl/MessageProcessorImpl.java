package ru.jafti.braintalk.message.processor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.jafti.braintalk.message.processor.api.MessageProcessor;
import ru.jafti.braintalk.message.processor.api.model.TalkersMessage;
import ru.jafti.braintalk.message.storage.api.model.StorableMessage;
import ru.jafti.braintalk.online.registry.OnlineMessageChannel;
import ru.jafti.braintalk.online.registry.SendMessageRequest;
//import ru.jafti.braintalk.online.registry.impl.RendezvousPoint;
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

    @Async
    @Override
    public void submit(TalkersMessage talkersMessage) {
        log.trace("Submit message {}", talkersMessage);

        //1. Проверить что пользователь, которому пересылается сообщение, существует
        String toNickname = talkersMessage.to().nickname();
        UUID toTalkerGuid = talkerProfileService.findByNickname(toNickname);
        if (toTalkerGuid == null) {
            log.warn("Talker not found by nickname {}", toNickname);
            return;
        }

        //2. Сгенерировать ID сообщения
        String messageId = UUID.randomUUID().toString();

        //3. Переслать сообщение толкеру если он онлайн
        sendToOnlineTalker(talkersMessage, toTalkerGuid, messageId);

        //4. Сохранить сообщение с message-storage
        StorableMessage message = StorableMessage.buildFrom(
                messageId,
                talkersMessage.from().nickname(),
                talkersMessage.from().talkerGuid(),
                toNickname,
                talkersMessage.content().rawContent()
        );

        messageStorage.store(message);
    }

    private void sendToOnlineTalker(TalkersMessage talkersMessage, UUID toTalkerGuid, String messageId) {
        if (messageChannel.isOnline(toTalkerGuid)) {
            SendMessageRequest messageRequest = new SendMessageRequest(
                    talkersMessage.to().nickname(),
                    talkersMessage.from().nickname(),
                    toTalkerGuid,
                    messageId,
                    new SendMessageRequest.Content(
                            talkersMessage.content().rawContent(),
                            SendMessageRequest.Content.ContentType.TEXT
                    )
            );

            messageChannel.send(messageRequest);
        }
    }
}
