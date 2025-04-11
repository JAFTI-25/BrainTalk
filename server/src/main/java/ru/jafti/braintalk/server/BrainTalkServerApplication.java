package ru.jafti.braintalk.server;

import ru.jafti.braintalk.server.connection.ConnectionHandler;
import ru.jafti.braintalk.server.persist.DbConnection;
import ru.jafti.braintalk.server.socket.SocketFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BrainTalkServerApplication {

    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        new BrainTalkServerApplication().start();
    }

    public void start() {
        new DbConnection().connect();

        final ServerSocket rawSocket = SocketFactory.Impl.raw().getSocket(9000);
        executor.submit(() -> acceptConnections(rawSocket));

        final ServerSocket sslSocket = SocketFactory.Impl.ssl().getSocket(9443);
        executor.submit(() -> acceptConnections(sslSocket));

    }

    private void acceptConnections(ServerSocket serverSocket) {
        try (serverSocket) {
            System.out.println("Server is listening on port " + serverSocket.getLocalPort());

            while (true) {
                Socket socket = serverSocket.accept();
                new ConnectionHandler(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}