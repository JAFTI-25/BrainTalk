package ru.jafti.braintalk.cli.command;


import org.springframework.stereotype.Component;
import ru.jafti.braintalk.cli.server_api.SendMessageClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SendCommand implements Command {

    private static final Pattern PATTERN = Pattern.compile("^/send +(?<talker>\\w+) +(?<message>.*)");

    private final SendMessageClient statefullClient;

    public SendCommand(SendMessageClient statefullClient) {
        this.statefullClient = statefullClient;
    }

    @Override
    public boolean isApplicable(String inputLine) {
        return inputLine != null && inputLine.startsWith("/send");
    }

    @Override
    public void execute(String inputLine) {
        // todo validate empty
        Matcher matcher = PATTERN.matcher(inputLine);
        if (matcher.find()) {
            String talker = matcher.group("talker");
            // todo validate user exists
            String message = matcher.group("message");
            statefullClient.send(talker, message);
        }
    }
}
