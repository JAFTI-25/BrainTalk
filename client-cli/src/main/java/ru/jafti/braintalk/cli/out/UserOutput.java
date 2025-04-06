package ru.jafti.braintalk.cli.out;

public interface UserOutput {
    void print(String message);

    class Impl {

        public static UserOutput get() {
            return TerminalOutput.INSTANCE;
        }
    }
}