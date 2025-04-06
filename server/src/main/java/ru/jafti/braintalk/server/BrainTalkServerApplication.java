package ru.jafti.braintalk.server;

import ru.jafti.braintalk.server.connection.ConnectionHandler;
import ru.jafti.braintalk.server.connection.ServerSocketFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BrainTalkServerApplication {

    public static void main(String[] args) throws IOException {
        new BrainTalkServerApplication().start();
    }

    public void start() {

        ServerSocketFactory rawSocketFactory = ServerSocketFactory.Impl.raw();
        ServerSocketFactory sslSocketFactory = ServerSocketFactory.Impl.ssl();

        new Thread(new Runnable() {
            @Override
            public void run() {
                openSocket(rawSocketFactory);
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                openSocket(sslSocketFactory);
            }
        }).start();
    }

    private static void openSocket(ServerSocketFactory socketFactory) {
        try (ServerSocket serverSocket = socketFactory.open()) {
            System.out.println("Server is listening connections on port " + serverSocket.getLocalPort());

            while (true) {
                Socket socket = serverSocket.accept();
                new ConnectionHandler(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}



