package ru.jafti.braintalk.cli.command;

import ru.jafti.braintalk.cli.out.UserOutput;

public class ExitCommand implements Command {

    private final UserOutput out;

    public ExitCommand(UserOutput output) {
        this.out = output;
    }

    @Override
    public boolean isApplicable(String inputLine) {
        return inputLine.startsWith("/exit");
    }

    @Override
    public void execute(String inputLine) {
        out.print("Buy!");
        System.exit(0);
    }
}
