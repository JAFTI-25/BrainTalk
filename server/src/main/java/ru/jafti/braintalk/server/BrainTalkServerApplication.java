package ru.jafti.braintalk.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
//import ru.jafti.braintalk.message.processor.MessageProcessorImpl;
import ru.jafti.braintalk.server.connection.ConnectionHandler;
import ru.jafti.braintalk.server.socket.SocketFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ComponentScan(basePackages = {
        "ru.jafti.braintalk",
        "ru.jafti.message.processor.impl"
})
@SpringBootApplication
public class BrainTalkServerApplication {

    private final ExecutorService executor = Executors.newFixedThreadPool(2);
    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(BrainTalkServerApplication.class, args);
    }

    @Bean
    public BrainTalkServerApplication boot() {
        var app = new BrainTalkServerApplication();
        app.start();
        return app;
    }

    private void start() {
        final ServerSocket rawSocket = SocketFactory.Impl.raw().getSocket(9000);
        executor.execute(() -> acceptConnections(rawSocket));

        final ServerSocket sslSocket = SocketFactory.Impl.ssl().getSocket(9443);
        executor.execute(() -> acceptConnections(sslSocket));
    }

    private void acceptConnections(ServerSocket serverSocket) {
        try {
            System.out.println("Server is listening on port " + serverSocket.getLocalPort());

            while (true) {
                Socket socket = serverSocket.accept();
                new ConnectionHandler(socket, applicationContext).start();
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}