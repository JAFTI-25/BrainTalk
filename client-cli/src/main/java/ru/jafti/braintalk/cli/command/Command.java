package ru.jafti.braintalk.cli.command;

public interface Command {

    boolean isApplicable(String inputLine);

    void execute(String inputLine);
}
