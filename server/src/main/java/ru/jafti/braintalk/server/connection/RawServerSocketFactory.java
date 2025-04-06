package ru.jafti.braintalk.server.connection;

import java.io.IOException;
import java.net.ServerSocket;

class RawServerSocketFactory implements ServerSocketFactory {

    private static final String HOST = "localhost";
    private static final int PORT = 9000;

    public ServerSocket open() {
        try {
            return new ServerSocket(PORT);
        } catch (IOException e) {
            System.err.println("Error during open server socker on port " + PORT);
            throw new RuntimeException(e);
        }
    }
}
