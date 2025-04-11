package ru.jafti.braintalk.server.socket;

import java.io.IOException;
import java.net.ServerSocket;

public class RawSocketFactory implements SocketFactory {
    @Override
    public ServerSocket getSocket(int port) {
        try {
            return new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Error during open server socker on port " + port);
            throw new RuntimeException(e);
        }
    }
}
