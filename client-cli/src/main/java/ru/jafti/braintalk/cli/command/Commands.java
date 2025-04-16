package ru.jafti.braintalk.cli.command;

import ru.jafti.braintalk.cli.mode.ModeHolder;
import ru.jafti.braintalk.cli.out.TerminalOutput;
import ru.jafti.braintalk.cli.server_api.ApiClient;

import java.util.ArrayList;


public class Commands {

    public static final Commands INSTANCE = new Commands();

    private ArrayList<Command> commands = new ArrayList<>();

    private Commands() {
        ModeHolder modeHolder = ModeHolder.INSTANCE;
        TerminalOutput output = TerminalOutput.INSTANCE;

        ApiClient api = ApiClient.INSTANCE;

        commands.add(new DefaultMessageCommand(output, modeHolder, api));
        commands.add(new LoginCommand(output, api));
        commands.add(new WhoCommand(output, api));
        commands.add(new SendCommand(api));
        commands.add(new GoCommand(output, modeHolder));
        commands.add(new QCommand(output, modeHolder));
        commands.add(new HelpCommand(output));
        commands.add(new ExitCommand(output));
    }

    public void execute(String inputLine) {
        for (Command command : commands) {
            if (command.isApplicable(inputLine)) {
                command.execute(inputLine);
            }
        }
    }
}
