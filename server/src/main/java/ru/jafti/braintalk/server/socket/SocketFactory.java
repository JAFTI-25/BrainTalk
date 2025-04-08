package ru.jafti.braintalk.server.socket;

import java.net.ServerSocket;

public interface SocketFactory {
    ServerSocket getSocket(int port);
}
