package ru.croc.univer.file_processor.mapper;

import ru.croc.univer.entity.Department;
import ru.croc.univer.enums.Program;

import java.text.MessageFormat;

public class DepartmentFileDataMapper implements FileDataMapper<Department> {

    @Override
    public Department mapToEntity(String line) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        int studyDuration = Integer.parseInt(parts[2]);
        int faculty = Integer.parseInt(parts[3]);
        Program program = Program.findByProgram(parts[4]).get();
        String code = parts[5];
        return new Department(id, name, studyDuration, faculty, program, code);
    }

    @Override
    public String mapToLine(Department entity) {
        return MessageFormat.format(
            "{0},{1},{2},{3},{4},{5}",
            entity.id(),
            entity.name(),
            entity.studyDuration(),
            entity.faculty(),
            entity.program().name(),
            entity.code()
        );
    }
}
