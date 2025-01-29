package ru.croc.univer.repository;

import ru.croc.univer.entity.Department;
import ru.croc.univer.entity.Student;
import ru.croc.univer.repository.filter.DepartmentFilterType;
import ru.croc.univer.repository.filter.Filter;
import ru.croc.univer.repository.filter.StudentFilterType;

import java.util.Collections;
import java.util.List;

public class StudentRepository implements UniverRepository<Student> {

    private final List<Student> students;
    private final DepartmentRepository departmentRepository;

    public StudentRepository(final List<Student> students, final DepartmentRepository departmentRepository) {
        this.students = students;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Student> findByFilter(Filter filter) {
        final StudentFilterType filterType = (StudentFilterType) filter.filterType();
        return switch (filterType) {
            case ADMISSION_YEAR -> filterByAdmissionYear(filter);
            case LAST_NAME -> filterByLastName(filter);
            case PROGRAM -> filterByProgram(filter);
            case DEPARTMENT_CODE -> filterByDepartmentCode(filter);
            default -> Collections.emptyList();
        };
    }

    private List<Student> filterByAdmissionYear(final Filter filter) {
        final String admissionYear = (String) filter.value();
        return students.stream()
            .filter(student -> {
                final String code = student.code();
                final String year = code.split("/")[1];
                return year.equals(admissionYear);
            })
            .toList();
    }

    private List<Student> filterByLastName(final Filter filter) {
        final String lastName = (String) filter.value();
        return students.stream()
            .filter(student -> student.lastName().toLowerCase().contains(lastName.toLowerCase()))
            .toList();
    }

    private List<Student> filterByProgram(final Filter filter) {
        final String program = (String) filter.value();
        final List<Department> foundDepartments = departmentRepository.findByFilter(new Filter(program, DepartmentFilterType.PROGRAM));
        if (foundDepartments.isEmpty()) {
            return Collections.emptyList();
        }
        final List<Integer> departmentIds = foundDepartments.stream().map(Department::id).toList();
        return students.stream().filter(student -> departmentIds.stream().anyMatch(id -> id == student.department())).toList();
    }

    private List<Student> filterByDepartmentCode(final Filter filter) {
        final String departmentCode = (String) filter.value();
        final List<Department> foundDepartments = departmentRepository.findByFilter(new Filter(departmentCode, DepartmentFilterType.CODE));
        if (foundDepartments.isEmpty()) {
            return Collections.emptyList();
        }
        final List<Integer> departmentIds = foundDepartments.stream().map(Department::id).toList();
        return students.stream().filter(student -> departmentIds.stream().anyMatch(id -> id == student.department())).toList();
    }

    @Override
    public void addEntity(Student entity) {
        students.add(entity);
    }
}
