package ru.jafti.braintalk.server;

import ru.jafti.braintalk.server.persist.DbConnection;
import ru.jafti.braintalk.server.persist.JdbcConnection;

public class ApplicationContext {
    public final static ApplicationContext INSTANCE = new ApplicationContext();

    public DbConnection getDbConnection() {
        return dbConnection;
    }

    public void setDbConnection(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public DbConnection dbConnection;


}
