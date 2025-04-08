package ru.jafti.braintalk.cli.command;

import ru.jafti.braintalk.cli.mode.ModeHolder;
import ru.jafti.braintalk.cli.out.UserOutput;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoCommand implements Command {

    private static final Pattern PATTERN = Pattern.compile("^/go +(?<user>\\w+)");

    private final UserOutput out;
    private final ModeHolder mode;

    public GoCommand(UserOutput output, ModeHolder modeHolder) {
        this.out = output;
        this.mode = modeHolder;
    }

    @Override
    public boolean isApplicable(String inputLine) {
        return inputLine != null && inputLine.startsWith("/go");
    }

    @Override
    public void execute(String inputLine) {
        Matcher matcher = PATTERN.matcher(inputLine);
        if (matcher.find()) {
            String user = matcher.group("user");
            // todo validate user exists
            mode.connectToUser(user);
            out.print("Connected to " + user);
        }
    }
}
