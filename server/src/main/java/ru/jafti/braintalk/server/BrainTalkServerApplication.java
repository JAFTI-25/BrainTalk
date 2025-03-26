package ru.jafti.braintalk.server;

import ru.jafti.braintalk.server.connection.ConnectionHandler;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.io.FileInputStream;
import java.net.Socket;
import java.security.KeyStore;

public class BrainTalkServerApplication {
    public static void main(String[] args) throws IOException {
        new BrainTalkServerApplication().start();
    }

    public void start() {
        try {
            char[] password = "password".toCharArray();

            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(getClass().getResourceAsStream("/server.keystore"), password);

            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, password);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, null);

            SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();

            try (var serverSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(9000)) {
                System.out.println("SSL Server is listening on port 9000");

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



