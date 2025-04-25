package ru.jafti.braintalk.message.storage.impl.persist;

import java.sql.Connection;

public interface DbConnection {
        Connection getConnection();
        void connect();
}
