package ru.jafti.braintalk.client.common.home;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TalkerGuidHolderTest {
    private TalkerGuidHolder uut;

    @TempDir
    Path tempHomeDir;

    @BeforeEach
    void setUp() {
        // Подменяем домашнюю папку перед каждым тестом
        System.setProperty("user.home", tempHomeDir.toString());
        uut = new TalkerGuidHolder();
    }

    @Test
    void testSaveAndLoad() {
        UUID expectedUuid = UUID.randomUUID();
        uut.save(expectedUuid);

        UUID loadedUuid = uut.load();

        assertNotNull(loadedUuid);
        assertEquals(expectedUuid, loadedUuid);
    }

    @Test
    void testLoadWhenFileDoesNotExist() {
        UUID loadedUuid = uut.load();
        assertNull(loadedUuid, "Если файл отсутствует или пустой, должен быть null");
    }

    @Test
    void testLoadWithInvalidGuid() throws IOException {
        // Создаем файл с неправильным содержимым
        Path badFile = tempHomeDir.resolve(".braintalk").resolve("talker_guid");
        Files.createDirectories(badFile.getParent());
        Files.writeString(badFile, "невалидный-guid");

        UUID loadedUuid = uut.load();
        assertNull(loadedUuid, "Если содержимое некорректное, должен быть null");
    }

    @Test
    void testSaveWithNullUuid() throws IOException {
        uut.save(null);

        Path file = tempHomeDir.resolve(".braintalk").resolve("talker_guid");

        assertFalse(Files.exists(file), "Файл не должен создаться");
    }

    @Test
    void testDirectoryAndFileCreated() {
        uut.save(UUID.randomUUID());

        Path directory = tempHomeDir.resolve(".braintalk");
        Path file = directory.resolve("talker_guid");

        assertTrue(Files.exists(directory));
        assertTrue(Files.exists(file));
    }

}