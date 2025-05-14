package ru.jafti.braintalk.cli.command;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.jafti.braintalk.cli.mode.ModeHolder;
import ru.jafti.braintalk.cli.out.TerminalOutput;
import ru.jafti.braintalk.cli.server_api.ApiClient;

import java.util.ArrayList;

@Component
public class Commands {

    private ArrayList<Command> commands = new ArrayList<>();

    private Commands(
            RegisterCommand registerCommand,
            LoginCommand loginCommand,
            SendCommand sendCommand,
            WhoCommand whoCommand,
            GoCommand goCommand,
            QCommand qCommand,
            HelpCommand helpCommand,
            ExitCommand exitCommand,
            DefaultMessageCommand defaultMessageCommand
    ) {
        commands.add(registerCommand);
        commands.add(loginCommand);
        commands.add(sendCommand);
        commands.add(whoCommand);
        commands.add(goCommand);
        commands.add(qCommand);
        commands.add(helpCommand);
        commands.add(exitCommand);
        commands.add(defaultMessageCommand);
    }

    public void execute(String inputLine) {
        for (Command command : commands) {
            if (command.isApplicable(inputLine)) {
                command.execute(inputLine);
                return;
            }
        }
    }
}
