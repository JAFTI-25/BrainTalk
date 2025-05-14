package ru.jafti.braintalk.cli.server_api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.jafti.braintalk.cli.connection.ConnectionFactory;
import ru.jafti.braintalk.cli.out.UserOutput;
import ru.jafti.braintalk.cli.uuid_loader.UUIDLoader;
import ru.jafti.braintalk.client.common.home.TalkerGuidLoader;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.UUID;

@Component
public class ApiClient implements SendMessageClient, ControlApiClient {

    private static final Logger log = LoggerFactory.getLogger(ApiClient.class);
    private final BufferedReader serverIn;
    private final PrintWriter serverOut;
    private final InputMessageHandler inputMessageHandler;

    public ApiClient(UserOutput userOutput,
                     ConnectionFactory connectionFactory,
                     TalkerGuidLoader talkerGuidLoader) {
        try {
            var connection = connectionFactory.connect();
            serverIn = connection.getReader();
            serverOut = connection.getWriter();

            autoLogin(talkerGuidLoader);

            inputMessageHandler = new InputMessageHandler(serverIn, userOutput);
            inputMessageHandler.start();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Can't connect to server");
        }
    }

    private void autoLogin(TalkerGuidLoader talkerGuidLoader) {
        UUID uuid = talkerGuidLoader.load();
        log.debug("UUID from file {}", uuid);
        if (uuid != null) {
            serverOut.println("/auto-login " + uuid);
            serverOut.flush();
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
