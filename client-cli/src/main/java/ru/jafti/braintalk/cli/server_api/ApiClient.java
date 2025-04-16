package ru.jafti.braintalk.cli.server_api;

import ru.jafti.braintalk.cli.connection.ConnectionFactory;
import ru.jafti.braintalk.cli.out.UserOutput;
import ru.jafti.braintalk.cli.uuid_loader.UUIDLoader;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.UUID;

public class ApiClient implements SendMessageClient, ControlApiClient {

    public static final ApiClient INSTANCE = new ApiClient();

    private final BufferedReader serverIn;
    private final PrintWriter serverOut;
    private final UserOutput userOutput;
    private final InputMessageHandler inputMessageHandler;

    private ApiClient() {
        this.userOutput = UserOutput.Impl.get();
        try {
            ConnectionFactory connectionFactory = ConnectionFactory.Impl.get();
            var connection = connectionFactory.connect();
            serverIn = connection.getReader();
            serverOut = connection.getWriter();

            UUID uuid = UUIDLoader.loadFromFile(null);
            serverOut.println("/auto-login " + uuid.toString());
            serverOut.flush();

            inputMessageHandler = new InputMessageHandler(serverIn, userOutput);
            inputMessageHandler.start();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Can't connect to server");
        }
    }

    @Override
    public void send(String toTalker, String message) {
        serverOut.println("/send " + toTalker + " " + message);
        serverOut.flush();
    }

    @Override
    public void who() {
        serverOut.println("/who");
        serverOut.flush();
    }

    @Override
    public void login(String talker) {
        serverOut.println("/login " + talker);
        serverOut.flush();
    }
}
