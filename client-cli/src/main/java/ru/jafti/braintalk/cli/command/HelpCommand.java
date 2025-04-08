package ru.jafti.braintalk.cli.command;

import ru.jafti.braintalk.cli.out.UserOutput;

public class HelpCommand implements Command {
    private static final String HELP_MESSAGE = """
                        Available commands:
                         /who - list active users
                         /add - add user to contact list
                         /send <user> <message> - to send direct message to the user
                         /go <user> - go to permanent conversation with to the user
                         /q - quit to default state
                         /exit - exit program
                        """;

    private final UserOutput out;

    public HelpCommand(UserOutput output) {
        this.out = output;
    }

    @Override
    public boolean isApplicable(String inputLine) {
        // todo добавить обработку случая когда ввели '/' или '/unknown' неизвестную команду
        return inputLine.startsWith("/help");
    }

    @Override
    public void execute(String inputLine) {
        out.print(HELP_MESSAGE);
    }
}
