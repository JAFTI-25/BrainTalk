package ru.jafti.braintalk.cli.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.jafti.braintalk.cli.out.UserOutput;
import ru.jafti.braintalk.client.common.home.TalkerGuidSaver;
import ru.jafti.braintalk.server.api.RegisterApi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegisterCommand implements Command {

    private static final Pattern PATTERN = Pattern.compile("^/register +(?<talker>\\w+)");
    private static final Logger log = LoggerFactory.getLogger(RegisterCommand.class);

    private final UserOutput out;
    private final RegisterApi api;
    private final TalkerGuidSaver talkerGuidSaver;

    public RegisterCommand(UserOutput out, RegisterApi api, TalkerGuidSaver talkerGuidSaver) {
        this.out = out;
        this.api = api;
        this.talkerGuidSaver = talkerGuidSaver;
    }

    @Override
    public boolean isApplicable(String inputLine) {
        return inputLine != null && inputLine.startsWith("/register");
    }

    @Override
    public void execute(String inputLine) {
        log.debug("Execute: {}", inputLine);
        Matcher matcher = PATTERN.matcher(inputLine);
        if (matcher.find()) {
            String talker = matcher.group("talker");
            RegisterApi.RegisterResponse register = api.register(new RegisterApi.RegisterRequest(talker));
            log.info("Register success {}", register.talkerGuid());
            talkerGuidSaver.save(register.talkerGuid());
        }
    }
}
