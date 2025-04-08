package ru.jafti.braintalk.cli.server_api;

import ru.jafti.braintalk.cli.out.UserOutput;

import java.io.BufferedReader;
import java.io.IOException;

public class InputMessageHandler extends Thread {

    private final BufferedReader serverInput;
    private final UserOutput userOutput;

    public InputMessageHandler(BufferedReader serverInput, UserOutput userOutput) {
        this.serverInput = serverInput;
        this.userOutput = userOutput;
    }

    @Override
    public void run() {
        try {
            readInput();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                serverInput.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                System.err.println("Can't close resource");
            }
        }
    }

    private void readInput() throws IOException {
        String inputLine;
        while ((inputLine = serverInput.readLine()) != null) {
            if (inputLine.isEmpty()) {
                continue;
            }
            userOutput.print(inputLine);
        }
    }
}
