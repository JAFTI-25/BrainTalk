package ru.jafti.braintalk.server;

import ru.jafti.braintalk.server.persist.DbConnection;

import java.util.UUID;

public class TalkerProfileServiceImpl implements TalkerProfileService {
    public TalkerProfileServiceImpl(DbConnection dbConnection) {
    }

    @Override
    public UUID findByNickname(String nickname) {
        return null;
    }

    @Override
    public UUID createWithNickname(String nickname) {
        return null;
    }
}
