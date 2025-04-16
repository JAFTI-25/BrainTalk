package ru.jafti.braintalk.cli.uuid_loader;

import java.util.UUID;

public class UUIDLoader {

    private UUIDLoader() {};

    public static UUID loadFromFile(String filePath) {
        return new UUID(0, 0);
    }
}
