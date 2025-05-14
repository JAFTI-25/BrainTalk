package ru.jafti.braintalk.server.controller;

import org.springframework.stereotype.Component;
import ru.jafti.braintalk.message.processor.api.MessageProcessor;
import ru.jafti.braintalk.message.processor.api.model.TalkersMessage;
import ru.jafti.braintalk.server.connection.Session;
import ru.jafti.braintalk.server.exception.MatchPatternException;
import ru.jafti.braintalk.talker.profile.api.TalkerProfileService;

import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class SendController implements Controller {
    private static final Pattern PATTERN = Pattern.compile("^/send +(?<talker>\\w+) +(?<message>.*)");
    private static final Pattern APPLICABLE_PATTERN = Pattern.compile("^/send.*");

    private final MessageProcessor messageProcessor;
    private final TalkerProfileService talkerProfileService;

    public SendController(MessageProcessor messageProcessor, TalkerProfileService talkerProfileService) {
        this.messageProcessor = messageProcessor;
        this.talkerProfileService = talkerProfileService;
    }

    public boolean isApplicable(String inputLine) {
        return APPLICABLE_PATTERN.matcher(inputLine).find();
    }

    public void apply(String inputLine, Session session) {
        var matcher = PATTERN.matcher(inputLine);
        if (matcher.find()) {
            String talker = matcher.group("talker");
            UUID toTalkerGuid = talkerProfileService.findByNickname(talker);            
            if (toTalkerGuid == null) {
                throw new MatchPatternException("User not found: " + talker);
            }
            
            String message = matcher.group("message");

            String fromTalker = session.getTalkerOwner();
            UUID fromTalkerGuid = session.getTalkerOwnerGuid();

            sendToMessageProcessor(fromTalker, fromTalkerGuid, talker, toTalkerGuid, message);

        } else {
            throw new MatchPatternException("/send <talker> <message>");
        }
    }

    private void sendToMessageProcessor(
            String fromTalkerNickName,
            UUID fromTalkerGuid,
            String toTalkerNickname,
            UUID toTalkerGuid,
            String message) {

        messageProcessor.submit(
                TalkersMessage.buildFrom(fromTalkerNickName, fromTalkerGuid,
                toTalkerNickname, toTalkerGuid, message)
        );
    }
}
