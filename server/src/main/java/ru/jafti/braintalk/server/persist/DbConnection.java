package ru.jafti.braintalk.server.persist;

import java.sql.Connection;

public interface DbConnection {
    Connection getConnection();

    void connect();
}
