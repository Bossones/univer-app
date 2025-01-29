package ru.croc.univer.file_processor;

import ru.croc.univer.entity.Student;
import ru.croc.univer.file_processor.mapper.FileDataMapper;

import java.nio.file.Path;

public class StudentFileProcessor extends AbstractFileProcessor<Student> {

    public StudentFileProcessor(FileDataMapper<Student> fileDataMapper) {
        super(Path.of("/students/students_list.csv"), fileDataMapper);
    }
}
