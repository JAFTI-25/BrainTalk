package ru.jafti.braintalk.server.socket;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import java.net.ServerSocket;
import java.security.KeyStore;

public class SSLSocketFactory implements SocketFactory {
    @Override
    public ServerSocket get_socket(int port) {
        try {
            char[] password = "password".toCharArray();

            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(getClass().getResourceAsStream("/server.keystore"), password);

            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, password);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, null);

            SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
            return sslServerSocketFactory.createServerSocket(port);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
