package ru.croc.univer.file_processor.mapper;

public interface FileDataMapper<T> {

    T mapToEntity(final String line);

    String mapToLine(final T entity);
}
