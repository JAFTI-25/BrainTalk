package ru.jafti.braintalk.server.connection;


import ru.jafti.braintalk.online.registry.Channel;

import java.util.UUID;

public interface Session extends Channel {
    void setLoggedIn(String talkerOwner, UUID talkerOwnerGuid);
    String getTalkerOwner();
    UUID getTalkerOwnerGuid();
}
