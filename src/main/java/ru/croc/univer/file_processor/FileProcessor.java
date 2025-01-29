package ru.croc.univer.file_processor;

import java.nio.file.Path;
import java.util.List;

public interface FileProcessor extends AutoCloseable {

    List<?> readFileData();

    boolean writeLine(final String line);

    static Path getResourcePath(String filePath) {
        return Path.of(FileProcessor.class.getResource(filePath).getPath());
    }
}
