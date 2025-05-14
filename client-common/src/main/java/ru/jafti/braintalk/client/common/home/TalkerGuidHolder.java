package ru.jafti.braintalk.client.common.home;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Component
public class TalkerGuidHolder implements TalkerGuidLoader, TalkerGuidSaver {

    private static final String BRAINTALK_HOME = ".braintalk";
    private static final String TALKER_GUID_FILE = "talker_guid";
    private static final Log log = LogFactory.getLog(TalkerGuidHolder.class);

    @Override
    public UUID load() {
        Path talkerGuidFile = getFileIfExists();

        if (talkerGuidFile == null) {
            log.debug("Talker file not exists yet");
            return null;
        }

        try (var fileStream =
                     new BufferedReader(
                             new InputStreamReader(
                                     new FileInputStream(talkerGuidFile.toFile())))) {

            String fileContent = fileStream.readLine();

            return tryReadGuid(fileContent);

        } catch (FileNotFoundException e) {
            System.out.println("Talker's Guid file is missing");
        } catch (Exception e) {
            System.out.println("Can't read guid file");
            e.printStackTrace();
        }
        return null;
    }

    private UUID tryReadGuid(String fileContent) {
        try {
            return UUID.fromString(fileContent);
        } catch (Exception e) {
            System.err.println("Bad talker Guid " + fileContent);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(UUID talkerGuid) {
        if (talkerGuid == null) {
            return;
        }

        Path talkerGuidFile = getFileAndCreateIfNotExists();

        try {
            Files.writeString(talkerGuidFile, talkerGuid.toString(),
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getFileIfExists() {
        String userHome = System.getProperty("user.home");
        Path directoryPath = Path.of(userHome, BRAINTALK_HOME);

        Path filePath = directoryPath.resolve(TALKER_GUID_FILE);

        if (Files.notExists(filePath)) {
            return null;
        }

        return Paths.get(userHome, BRAINTALK_HOME, TALKER_GUID_FILE);
    }

    private Path getFileAndCreateIfNotExists() {
        try {
            String userHome = System.getProperty("user.home");
            Path directoryPath = Path.of(userHome, BRAINTALK_HOME);

            if (Files.notExists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            Path filePath = directoryPath.resolve(TALKER_GUID_FILE);

            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
            }

            return Paths.get(userHome, BRAINTALK_HOME, TALKER_GUID_FILE);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
