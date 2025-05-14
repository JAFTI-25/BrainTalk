package ru.jafti.braintalk.cli.command;

import org.springframework.stereotype.Component;
import ru.jafti.braintalk.cli.out.UserOutput;
import ru.jafti.braintalk.cli.server_api.ControlApiClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LoginCommand implements Command {

    private static final Pattern PATTERN = Pattern.compile("^/login +(?<talker>\\w+)");

    private final UserOutput out;
    private final ControlApiClient api;

    public LoginCommand(UserOutput out, ControlApiClient api) {
        this.out = out;
        this.api = api;
    }

    @Override
    public boolean isApplicable(String inputLine) {
        return inputLine != null && inputLine.startsWith("/login");
    }

    @Override
    public void execute(String inputLine) {
        Matcher matcher = PATTERN.matcher(inputLine);
        if (matcher.find()) {
            String talker = matcher.group("talker");
            api.login(talker);
        }
    }
}
