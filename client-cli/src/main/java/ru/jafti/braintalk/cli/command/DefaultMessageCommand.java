package ru.jafti.braintalk.cli.command;

import ru.jafti.braintalk.cli.mode.ModeHolder;
import ru.jafti.braintalk.cli.out.UserOutput;
import ru.jafti.braintalk.cli.server_api.SendMessageClient;

public class DefaultMessageCommand implements Command {

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
        return inputLine != null && !inputLine.startsWith("/");
    }

    @Override
    public void execute(String inputLine) {

        if (mode.isConnected()) {
            api.send(mode.getConnectedUser(), inputLine);
        } else {
            out.print("Use '/go <talker>' command");
        }
    }
}
