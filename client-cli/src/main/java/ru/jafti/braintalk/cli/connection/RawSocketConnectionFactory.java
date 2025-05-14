package ru.jafti.braintalk.cli.connection;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@Component
@ConditionalOnProperty(name = "ssl.socket.enabled", havingValue = "false")
class RawSocketConnectionFactory implements ConnectionFactory {

    private static final String HOST = "localhost";
    private static final int PORT = 9000;

    @Override
    public Connection connect() {

        try {
            Socket clientSocket = new Socket(HOST, PORT);
            System.out.println("-- Connected to the server");
            return new RawSocketWrapper(clientSocket);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private static class RawSocketWrapper implements Connection {
        private final Socket socket;
        private final BufferedReader reader;
        private final PrintWriter writer;

        private RawSocketWrapper(Socket socket) {
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
