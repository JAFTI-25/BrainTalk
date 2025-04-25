package ru.jafti.braintalk.online.registry.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.jafti.braintalk.online.message.channel.api.OnlineMessageChannel;
import ru.jafti.braintalk.online.message.channel.api.OutgoingMessage;
import ru.jafti.braintalk.online.message.channel.api.SignalMessage;
import ru.jafti.braintalk.online.registry.Channel;
import ru.jafti.braintalk.online.registry.GoInRequest;
import ru.jafti.braintalk.online.registry.GoOutRequest;
import ru.jafti.braintalk.online.registry.OnlineRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static ru.jafti.braintalk.common.CommonConstants.SYSTEM_TALKER;
import static ru.jafti.braintalk.online.message.channel.api.OutgoingMessage.Content.ContentType.TEXT;

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
    public void signal(SignalMessage message) {
        UUID toTalkerGuid = message.to().talkerGuid();
        if (!isOnline(toTalkerGuid)) {
            return;
        }

        outputStreams.get(toTalkerGuid)
                .sendToOwner(SYSTEM_TALKER, message.signalText());
    }

    @Override
    public void send(OutgoingMessage message) {
        UUID toTalkerGuid = message.to().talkerGuid();
        String messageId = message.messageId();

        if (!isOnline(toTalkerGuid)) {
            log.warn("Talker is not online {} messageId {}", toTalkerGuid, messageId);
            return;
        }

        OutgoingMessage.Content content = message.content();
        if (content.contentType() != TEXT) {
            log.warn("Unsupported content {} messageId {}", content.contentType(), messageId);
            return;
        }

        outputStreams.get(toTalkerGuid)
                .sendToOwner(message.from().nickname(), message.content().rawContent());
    }
}
