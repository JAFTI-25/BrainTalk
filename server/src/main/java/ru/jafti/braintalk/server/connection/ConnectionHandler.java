package ru.jafti.braintalk.server.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import ru.jafti.braintalk.online.registry.GoOutRequest;
import ru.jafti.braintalk.online.registry.OnlineRegistry;
import ru.jafti.braintalk.server.controller.Controllers;
import ru.jafti.braintalk.server.exception.MatchPatternException;
import ru.jafti.braintalk.server.exception.UserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.UUID;

import static ru.jafti.braintalk.common.CommonConstants.SYSTEM_TALKER;
import static ru.jafti.braintalk.server.Constants.*;

public class ConnectionHandler extends Thread implements Session {

    private static final Logger log = LoggerFactory.getLogger(ConnectionHandler.class);

    // Эндпоинты, доступные без авторизации
    private static final List<String> PUBLIC_ENDPOINTS = List.of(LOGIN, AUTO_LOGIN, REGISTER);

    private final Socket clientSocket;
    private final Controllers controllers;
    private final OnlineRegistry onlineRegistry;
    private BufferedReader in;
    private PrintWriter out;
    private boolean loggedIn;
    private String talkerOwner;
    private UUID talkerOwnerGuid;

    public ConnectionHandler(Socket clientSocket, ApplicationContext applicationContext) {
        this.clientSocket = clientSocket;
        this.controllers = applicationContext.getBean(Controllers.class);
        this.onlineRegistry = applicationContext.getBean(OnlineRegistry.class);
    }

    @Override
    public void run() {
        try {
            handleClientConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                log.debug("Client disconnected");
                in.close();
                out.close();
                clientSocket.close();
                if (loggedIn) {
                    onlineRegistry.goOut(new GoOutRequest(talkerOwnerGuid, talkerOwner));
                }
            } catch (IOException e1) {
                System.err.println("Can't close resource");
            }
        }
    }

    private void handleClientConnection() throws IOException {
        log.debug("Client connected");

        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.isEmpty()) {
                continue;
            }
            if (!loggedIn && !isPublicEndpoint(inputLine)) {
                sendToOwner(SYSTEM_TALKER, "You are not logged in. Enter your login with '/login' command");
                continue;
            }

            apply(inputLine);
        }
    }

    private void apply(String inputLine) {
        try {
            controllers.apply(inputLine, this);
        } catch (MatchPatternException e) {
            sendToOwner(SYSTEM_TALKER, "Syntax error. Use: " + e.getMessageWithCorrectSyntax());
            ;
        } catch (UserException e) {
            sendToOwner(SYSTEM_TALKER, e.getMessage());
        } catch (Exception e) {
            log.error("System error", e);
            sendToOwner(SYSTEM_TALKER, "Sorry, system error");
            ;
        }
    }

    private boolean isPublicEndpoint(String inputLine) {
        for (String publicEndpoint : PUBLIC_ENDPOINTS) {
            if (inputLine.startsWith(publicEndpoint)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void sendToOwner(String fromTalker, String message) {
        out.println(fromTalker + ": " + message);
    }

    @Override
    public void setLoggedIn(String talkerOwner, UUID talkerOwnerGuid) {
        this.loggedIn = true;
        this.talkerOwner = talkerOwner;
        this.talkerOwnerGuid = talkerOwnerGuid;
    }

    @Override
    public String getTalkerOwner() {
        return talkerOwner;
    }

    @Override
    public UUID getTalkerOwnerGuid() {
        return talkerOwnerGuid;
    }
}


