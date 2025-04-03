package ru.jafti.braintalk.server.connection;

import ru.jafti.braintalk.server.RendezvousPoint;
import ru.jafti.braintalk.server.service.TalkerProfileService;
import ru.jafti.braintalk.server.controller.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

public class ConnectionHandler extends Thread implements Session {

    private final Socket clientSocket;
    private final Controllers controllers;
    private final RendezvousPoint rendezvousPoint;
    private BufferedReader in;
    private PrintWriter out;


    private TalkerProfileService talkerProfileService; //Todo: should be assigned later

    private boolean loggedIn;
    private String talkerOwner;

    public ConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.controllers = Controllers.INSTANCE;
        this.rendezvousPoint = RendezvousPoint.INSTANCE;

    }

    @Override
    public void run() {
        try {
            handleClientConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
                rendezvousPoint.goOut(talkerOwner);
            } catch (IOException e1) {
                System.err.println("Can't close resource");
            }
        }
    }

    private void handleClientConnection() throws IOException {
        System.out.println("-- Client connected");

        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);

        String inputLine = in.readLine();
        boolean autoLoggedIn =  tryAutoLogin(inputLine);

        if(! autoLoggedIn){
            out.println("Hi! Tell me your login first please :)..");
        }
        while ((inputLine = in.readLine()) != null) {
            processInputLine(inputLine);
        }
    }

    private void processInputLine(String inputLine){
        if (inputLine.isEmpty()) {
            return;
        }
        if (!loggedIn && !inputLine.startsWith("/login")) {
            out.println("Enter your login with '/login' command");
            return;
        }
        controllers.apply(inputLine, this);
    }


    private boolean tryAutoLogin(String inputLine) {
        if (inputLine.isEmpty()) {
            return false;
        }
        if (!loggedIn) {
            String TalkerLogin = talkerProfileService.findById( UUID.fromString(inputLine));
            if(TalkerLogin != null){
                String loginStr = "/login " + TalkerLogin;
                controllers.apply(loginStr, this);
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
    public void setLoggedIn(String talkerOwner) {
        this.loggedIn = true;
        this.talkerOwner = talkerOwner;
    }

    @Override
    public String getTalkerOwner() {
        return talkerOwner;
    }
}


