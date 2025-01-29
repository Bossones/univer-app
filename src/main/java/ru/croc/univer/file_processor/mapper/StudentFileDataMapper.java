package ru.croc.univer.file_processor.mapper;

import ru.croc.univer.entity.Student;

import java.text.MessageFormat;

public class StudentFileDataMapper implements FileDataMapper<Student> {

    @Override
    public Student mapToEntity(String line) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        String lastName = parts[1];
        String firstName = parts[2];
        String middleName = parts[3];
        int age = Integer.parseInt(parts[4]);
        int department = Integer.parseInt(parts[5]);
        int studyYear = Integer.parseInt(parts[6]);
        String code = parts[7];
        return new Student(id, lastName, firstName, middleName, age, department, studyYear, code);
    }

    @Override
    public String mapToLine(Student entity) {
        return MessageFormat.format(
            "{0},{1},{2},{3},{4},{5},{6},{7}",
            entity.id(),
            entity.lastName(),
            entity.firstName(),
            entity.middleName(),
            entity.age(),
            entity.department(),
            entity.studyYear(),
            entity.code()
        );
    }
}
