package ru.croc.univer.validation.student;

import ru.croc.univer.dto.AddStudentDto;
import ru.croc.univer.entity.Department;
import ru.croc.univer.validation.ValidationResult;
import ru.croc.univer.validation.Validator;

import java.util.List;

public class DepartmentCodeStudentValidator implements Validator<AddStudentDto> {

    private final List<Department> departments;

    public DepartmentCodeStudentValidator(List<Department> departments) {
        this.departments = departments;
    }

    @Override
    public ValidationResult validate(AddStudentDto dto) {
        final String departmentCode = dto.departmentCode();
        boolean departmentExists = departments.stream().anyMatch(department -> department.code().equals(departmentCode));
        if (departmentExists) {
            return new ValidationResult(true, null);
        }
        return new ValidationResult(false, "DEPARTMENT_CODE");
    }
}
