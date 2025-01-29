package ru.croc.univer.file_processor;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import ru.croc.univer.file_processor.mapper.FileDataMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFileProcessor<T> implements FileProcessor {

    private final Path filePath;
    private final FileDataMapper<T> fileDataMapper;
    private Writer fileOS;

    public AbstractFileProcessor(Path filePath, FileDataMapper<T> fileDataMapper) {
        this.filePath = FileProcessor.getResourcePath(filePath.toString());
        this.fileDataMapper = fileDataMapper;
    }

    @Override
    public List<T> readFileData() {
        try (var reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line = null;
            List<T> data = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                T entity = fileDataMapper.mapToEntity(line);
                data.add(entity);
            }
            return data;
        } catch (IOException ioException) {
            throw new UncheckedIOException("Нет возможности открыть файл для чтения данных", ioException);
        }
    }

    @Override
    public boolean writeLine(final String line) {
        if (isNull(fileOS)) {
            try {
                fileOS = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND);
            } catch (IOException ioException) {
                throw new UncheckedIOException("Нет возможности открыть файл для записи данных", ioException);
            }
        }
        try {
            fileOS.write(line);
            fileOS.flush();
            return true;
        } catch (IOException ioException) {
            throw new UncheckedIOException("Нет возможности добавить новую строчку в файл", ioException);
        }
    }

    public boolean writeLine(final T entity) {
        final String line = fileDataMapper.mapToLine(entity);
        return writeLine(line);
    }

    @Override
    public void close() throws Exception {
        if (nonNull(fileOS)) {
            try {
                fileOS.close();
            } catch (IOException ioException) {
                throw new UncheckedIOException("Нет возможности закрыть источник данных", ioException);
            } finally {
                fileOS = null;
            }
        }
    }
}
