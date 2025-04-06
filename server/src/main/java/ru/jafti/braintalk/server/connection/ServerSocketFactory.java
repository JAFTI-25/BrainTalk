package ru.jafti.braintalk.server.connection;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

public interface ServerSocketFactory {

    ServerSocket open();

    class Impl {
        public static ServerSocketFactory raw() {
            return new RawServerSocketFactory();
        }

        public static ServerSocketFactory ssl() {
            return new SSLServerSockerFactory();
        }
    }
}
