package ru.jafti.braintalk.cli;

import ru.jafti.braintalk.cli.command.Commands;
import ru.jafti.braintalk.cli.out.UserOutput;
import java.util.Scanner;

public class BrainTalkClient {

    private Commands commands = Commands.INSTANCE;
    private UserOutput out = UserOutput.Impl.get();

    public void start() {
        out.print("Cli app started");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            commands.execute(scanner.nextLine());
        }
    }

    public static void main(String[] args) {
        new BrainTalkClient().start();
    }
}
