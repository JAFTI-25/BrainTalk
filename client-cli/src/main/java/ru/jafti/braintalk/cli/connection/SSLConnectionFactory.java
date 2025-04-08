package ru.jafti.braintalk.cli.connection;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

class SSLConnectionFactory implements ConnectionFactory {

    private static final String HOST = "localhost";
    private static final int PORT = 9443;

    @Override
    public Connection connect() {

        SSLContext sslContext = getSslContext();
        SSLSocketFactory factory = sslContext.getSocketFactory();
        SSLSocket socket = getSslSocket(factory);

        return new SSLSocketWrapper(socket);
    }

    private SSLContext getSslContext() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
            }}, new SecureRandom());
            return sslContext;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private SSLSocket getSslSocket(SSLSocketFactory factory) {
        SSLSocket socket;
        try {
            socket = (SSLSocket) factory.createSocket(HOST, PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return socket;
    }

    private static class SSLSocketWrapper implements Connection {

        private final SSLSocket socket;
        private final BufferedReader reader;
        private final PrintWriter writer;

        public SSLSocketWrapper(SSLSocket socket) {
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                writer = new PrintWriter(socket.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            this.socket = socket;
        }

        @Override
        public BufferedReader getReader() {
            return reader;
        }

        @Override
        public PrintWriter getWriter() {
            return writer;
        }

        @Override
        public void close() throws Exception {
            reader.close();
            writer.close();
            socket.close();
        }
    }
}
