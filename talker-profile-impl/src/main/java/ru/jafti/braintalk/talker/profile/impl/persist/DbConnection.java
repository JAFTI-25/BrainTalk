package ru.jafti.braintalk.talker.profile.impl.persist;

import java.sql.Connection;

public interface DbConnection {
    Connection getConnection();

    void connect();
}
