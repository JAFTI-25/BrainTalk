package ru.jafti.braintalk.cli.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.jafti.braintalk.cli.mode.ModeHolder;
import ru.jafti.braintalk.cli.out.UserOutput;
import ru.jafti.braintalk.cli.server_api.SendMessageClient;

@Component
public class DefaultMessageCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(DefaultMessageCommand.class);
    private final UserOutput out;
    private final ModeHolder mode;
    private final SendMessageClient api;

    public DefaultMessageCommand(UserOutput output, ModeHolder modeHolder, SendMessageClient api) {
        this.out = output;
        this.mode = modeHolder;
        this.api = api;
    }

    @Override
    public boolean isApplicable(String inputLine) {
        return inputLine != null && inputLine.startsWith("/");
    }

    @Override
    public void execute(String inputLine) {
        out.print("Unknown command");
    }
}
