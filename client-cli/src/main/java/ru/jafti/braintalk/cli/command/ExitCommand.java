package ru.jafti.braintalk.cli.command;

import org.springframework.stereotype.Component;
import ru.jafti.braintalk.cli.out.UserOutput;

@Component
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
