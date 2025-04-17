package ru.jafti.braintalk.server.connection;

import java.util.UUID;

public interface Session extends Channel{
    void setLoggedIn(String talkerOwner, UUID talkerOwnerGuid);
    String getTalkerOwner();
    UUID getTalkerOwnerGuid();
}
