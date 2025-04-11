package ru.jafti.braintalk.server.socket;

import java.net.ServerSocket;

public interface SocketFactory {
    ServerSocket getSocket(int port);

    class Impl {
        public static SocketFactory raw() {
            return new RawSocketFactory();
        }

        public static SocketFactory ssl() {
            return new SSLSocketFactory();
        }
    }
}
