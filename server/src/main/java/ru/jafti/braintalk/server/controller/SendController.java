package ru.jafti.braintalk.server.controller;

import org.springframework.stereotype.Component;
import ru.jafti.braintalk.message.processor.api.MessageProcessor;
import ru.jafti.braintalk.message.processor.api.model.TalkersMessage;
import ru.jafti.braintalk.server.connection.Session;
import ru.jafti.braintalk.server.exception.MatchPatternException;
import ru.jafti.braintalk.server.snowflake.MessageIdGenerator;
import ru.jafti.braintalk.server.snowflake.MessageIdGeneratorImpl;

import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class SendController implements Controller {
    private static final Pattern PATTERN = Pattern.compile("^/send +(?<talker>\\w+) +(?<message>.*)");
    private static final Pattern APPLICABLE_PATTERN = Pattern.compile("^/send.*");

    private final MessageProcessor messageProcessor;
    private final MessageIdGenerator messageIdGenerator;

    public SendController(MessageProcessor messageProcessor, MessageIdGenerator messageIdGenerator) {
        this.messageProcessor = messageProcessor;
        this.messageIdGenerator = messageIdGenerator;
    }

    public boolean isApplicable(String inputLine) {
        return APPLICABLE_PATTERN.matcher(inputLine).find();
    }

    public void apply(String inputLine, Session session) {
        var matcher = PATTERN.matcher(inputLine);
        if (matcher.find()) {
            String talker = matcher.group("talker");
            String message = matcher.group("message");
            String messageId = String.valueOf(messageIdGenerator.generate()); // выдали ID сообщению

            String fromTalker = session.getTalkerOwner();
            UUID fromTalkerGuid = session.getTalkerOwnerGuid();

            sendToMessageProcessor(fromTalker, fromTalkerGuid, talker, message, messageId);

        } else {
            throw new MatchPatternException("/send <talker> <message>");
        }
    }

    private void sendToMessageProcessor(
            String fromTalkerNickName,
            UUID fromTalkerGuid,
            String toTalkerNickname,
            String message,
            String messageId) {

        messageProcessor.submit(
                TalkersMessage.buildFrom(fromTalkerNickName, fromTalkerGuid, toTalkerNickname, message, messageId)
        );
    }
}
