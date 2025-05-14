package ru.jafti.braintalk.cli.connection;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface ConnectionFactory {

    Connection connect();

    interface Connection extends AutoCloseable {
        BufferedReader getReader();
        PrintWriter getWriter();
    }
}
