package ru.croc.univer.file_processor.mapper;

import ru.croc.univer.entity.Faculty;

import java.text.MessageFormat;

public class FacultyFileDataMapper implements FileDataMapper<Faculty> {

    @Override
    public Faculty mapToEntity(String line) {
        String[] split = line.split(",");
        int id = Integer.parseInt(split[0]);
        String name = split[1];
        String code = split[2];
        return new Faculty(id, name, code);
    }

    @Override
    public String mapToLine(Faculty entity) {
        return MessageFormat.format("{0},{1},{2}", entity.id(), entity.name(), entity.code());
    }
}
