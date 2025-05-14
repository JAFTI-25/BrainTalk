package ru.jafti.braintalk.message.storage.persist;

import java.sql.Connection;

public interface DbConnection {
        Connection getConnection();
        void connect();
}
