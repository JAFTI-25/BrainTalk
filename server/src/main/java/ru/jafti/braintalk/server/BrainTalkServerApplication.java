package ru.jafti.braintalk.server;

import ru.jafti.braintalk.server.connection.ConnectionHandler;
import ru.jafti.braintalk.server.persist.JdbcConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BrainTalkServerApplication {

    public static void main(String[] args) throws IOException {
        new BrainTalkServerApplication().start();
    }

    public void start() {
        new JdbcConnection().connect();

        try (var serverSocket = new ServerSocket(9000)) {
            System.out.println("Server is listening connections on port " + 9000);

            while (true) {
                Socket accept = serverSocket.accept();
                new ConnectionHandler(accept).start();
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}



