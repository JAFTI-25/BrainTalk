package ru.jafti.braintalk.cli.command;


import ru.jafti.braintalk.cli.out.UserOutput;
import ru.jafti.braintalk.cli.server_api.ControlApiClient;

public class WhoCommand implements Command {

    private final UserOutput out;
    private final ControlApiClient api;

    public WhoCommand(UserOutput output, ControlApiClient api) {
        this.out = output;
        this.api = api;
    }

    @Override
    public boolean isApplicable(String inputLine) {
        return inputLine != null && inputLine.startsWith("/who");
    }

    @Override
    public void execute(String inputLine) {
        api.who();
    }
}

