package ru.jafti.braintalk.cli.out;


import org.springframework.stereotype.Component;
import ru.jafti.braintalk.cli.mode.ModeHolder;

@Component
public class TerminalOutput implements UserOutput {

    private static final String DEFAULT_PROMPT = "#";

    private final ModeHolder modeHolder;

    private TerminalOutput (ModeHolder modeHolder) {
        this.modeHolder = modeHolder;
    }

    @Override
    public void print(String message) {
        System.out.println(message);
        showPrompt();
    }

    private void showPrompt() {
        if (modeHolder.isConnected()) {
            System.out.print(modeHolder.getConnectedUser() + DEFAULT_PROMPT);
        } else {
            System.out.print(DEFAULT_PROMPT);
        }
    }
}