package ru.jafti.braintalk.online.registry.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.jafti.braintalk.common.CommonConstants;
import ru.jafti.braintalk.online.registry.Channel;
import ru.jafti.braintalk.online.registry.GoInRequest;
import ru.jafti.braintalk.online.registry.GoOutRequest;
import ru.jafti.braintalk.online.registry.OnlineMessageChannel;
import ru.jafti.braintalk.online.registry.OnlineRegistry;
import ru.jafti.braintalk.online.registry.SendMessageRequest;
import ru.jafti.braintalk.online.registry.SignalMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RendezvousPoint implements OnlineRegistry, OnlineMessageChannel {

    private static final Logger log = LoggerFactory.getLogger(RendezvousPoint.class);

    private final List<String> activeTalkers = new ArrayList<>();
    private final Map<UUID, Channel> outputStreams = new ConcurrentHashMap<>();

    @Override
    public void goIn(GoInRequest request) {
        activeTalkers.add(request.nickname());
        outputStreams.put(request.talkerGuid(), request.channel());
    }

    @Override
    public void goOut(GoOutRequest request) {
        activeTalkers.remove(request.nickname());
        outputStreams.remove(request.talkerGuid());
    }

    @Override
    public boolean isOnline(UUID talkerGuid) {
        return outputStreams.containsKey(talkerGuid);
    }

    @Override
    public List<String> getActiveTalkers() {
        return activeTalkers;
    }

    @Override
    public void send(SendMessageRequest request) {
        UUID toTalkerGuid = request.toTalkerGuid();
        String messageId = request.messageId();

        if (!isOnline(toTalkerGuid)) {
            log.warn("Talker is not online {} messageId {}", toTalkerGuid, messageId);
            return;
        }

        SendMessageRequest.Content content = request.content();
        if (content.contentType() != SendMessageRequest.Content.ContentType.TEXT) {
            log.warn("Unsupported content {} messageId {}", content.contentType(), messageId);
            return;
        }

        outputStreams.get(request.toTalkerGuid())
                .sendToOwner(request.fromTalker(), request.content().rawContent());
    }

    @Override
    public void signal(SignalMessage message) {
        UUID toTalkerGuid = message.to().talkerGuid();

        if (!isOnline(toTalkerGuid)) {
            log.warn("Talker is not online to receive signal message: {}", toTalkerGuid);
            return;
        }

        String signalText = message.signalText();

        outputStreams.get(toTalkerGuid)
                .sendToOwner(CommonConstants.SYSTEM_TALKER, signalText);

        log.debug("Signal message sent to {}: {}", toTalkerGuid, signalText);
    }
}
