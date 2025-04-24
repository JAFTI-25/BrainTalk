package ru.jafti.persist;

import java.sql.Connection;

public interface DbConnection {
        Connection getConnection();
        void connect();
}
