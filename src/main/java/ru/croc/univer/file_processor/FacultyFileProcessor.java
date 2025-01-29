package ru.croc.univer.file_processor;

import ru.croc.univer.entity.Faculty;
import ru.croc.univer.file_processor.mapper.FacultyFileDataMapper;

import java.nio.file.Path;

public class FacultyFileProcessor extends AbstractFileProcessor<Faculty> {

    public FacultyFileProcessor(final FacultyFileDataMapper facultyFileDataMapper) {
        super(Path.of("/university/faculties.csv"), facultyFileDataMapper);
    }
}
