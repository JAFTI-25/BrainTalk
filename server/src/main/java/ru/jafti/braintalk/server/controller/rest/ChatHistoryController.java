package ru.jafti.braintalk.server.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.jafti.braintalk.message.storage.api.MessageFetcher;
import ru.jafti.braintalk.message.storage.api.model.ChatMessage;

import java.util.List;
import java.util.UUID;

@RestController
public class ChatHistoryController {

    private final MessageFetcher messageFetcher;

    public ChatHistoryController(MessageFetcher messageFetcher) {
        this.messageFetcher = messageFetcher;
    }

    @GetMapping("/chat/history/from/{fromTalker}")
    public List<ChatMessage> findByFromTalker(@PathVariable("fromTalker") UUID fromTalker) {
        return messageFetcher.findByFromTalker(fromTalker);
    }

    @GetMapping("/chat/history/to/{toTalker}")
    public List<ChatMessage> findByToTalker(@PathVariable("toTalker") UUID toTalker) {
        return messageFetcher.findByToTalker(toTalker);
    }

    @GetMapping("/chat/history/between/{talker1}/{talker2}")
    public List<ChatMessage> findConversationBetween(@PathVariable("talker1") UUID talker1,
                                                     @PathVariable("talker2") UUID talker2) {
        return messageFetcher.findConversationBetween(talker1, talker2);
    }
}
