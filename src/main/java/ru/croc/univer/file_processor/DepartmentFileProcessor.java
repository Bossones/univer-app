package ru.croc.univer.file_processor;

import ru.croc.univer.entity.Department;
import ru.croc.univer.file_processor.mapper.DepartmentFileDataMapper;

import java.nio.file.Path;

public class DepartmentFileProcessor extends AbstractFileProcessor<Department> {

    public DepartmentFileProcessor(DepartmentFileDataMapper fileDataMapper) {
        super(Path.of("/university/departments.csv"), fileDataMapper);
    }
}
