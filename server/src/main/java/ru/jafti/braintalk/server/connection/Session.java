package ru.jafti.braintalk.server.connection;

public interface Session extends Channel{
    void setLoggedIn(String talkerOwner);
    String getTalkerOwner();
}
