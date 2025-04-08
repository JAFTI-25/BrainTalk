package ru.jafti.braintalk.server;

import ru.jafti.braintalk.server.connection.ConnectionHandler;
import ru.jafti.braintalk.server.persist.DbConnection;
import ru.jafti.braintalk.server.socket.SSLSocketFactory;
import ru.jafti.braintalk.server.socket.SocketFactory;

import java.net.Socket;

public class BrainTalkServerApplication {
    public static void main(String[] args) {
        new BrainTalkServerApplication().start();
    }

    public void start() {
        new DbConnection().connect();
        try {
            SocketFactory socketFactory = new SSLSocketFactory();
            try (var serverSocket = socketFactory.getSocket(9443)) {
                System.out.println("Server is listening on port 9443");

                while (true) {
                    Socket socket = serverSocket.accept();
                    new ConnectionHandler(socket).start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}