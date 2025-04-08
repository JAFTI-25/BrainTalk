package ru.jafti.braintalk.cli.out;


import ru.jafti.braintalk.cli.mode.ModeHolder;

public class TerminalOutput implements UserOutput {

    public static final TerminalOutput INSTANCE = new TerminalOutput();

    private static final String DEFAULT_PROMPT = "#";

    private final ModeHolder modeHolder;

    private TerminalOutput () {
        this.modeHolder = ModeHolder.INSTANCE;
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