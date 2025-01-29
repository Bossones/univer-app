package ru.croc.univer.repository;

import ru.croc.univer.entity.Department;
import ru.croc.univer.enums.Program;
import ru.croc.univer.repository.filter.DepartmentFilterType;
import ru.croc.univer.repository.filter.Filter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DepartmentRepository implements UniverRepository<Department> {

    private final List<Department> departments;

    public DepartmentRepository(List<Department> departments) {
        this.departments = departments;
    }

    @Override
    public List<Department> findByFilter(Filter filter) {
        final DepartmentFilterType departmentFilterType = (DepartmentFilterType) filter.filterType();
        return switch (departmentFilterType) {
            case CODE -> findByCode(filter);
            case PROGRAM -> findByProgram(filter);
        };
    }

    @Override
    public void addEntity(Department entity) {
        departments.add(entity);
    }

    private List<Department> findByCode(Filter filter) {
        final String code = (String) filter.value();
        return departments.stream().filter(department -> department.code().equals(code)).toList();
    }

    private List<Department> findByProgram(Filter filter) {
        final String programStr = (String) filter.value();
        final Optional<Program> program = Program.findByProgram(programStr);
        if (program.isPresent()) {
            return departments.stream().filter(department -> department.program().equals(program.get())).toList();
        }
        return Collections.emptyList();
    }
}
