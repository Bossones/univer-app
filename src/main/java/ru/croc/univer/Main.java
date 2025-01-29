package ru.croc.univer;

import ru.croc.univer.dto.AddStudentDto;
import ru.croc.univer.entity.Department;
import ru.croc.univer.entity.Student;
import ru.croc.univer.file_processor.DepartmentFileProcessor;
import ru.croc.univer.file_processor.FacultyFileProcessor;
import ru.croc.univer.file_processor.StudentFileProcessor;
import ru.croc.univer.file_processor.mapper.DepartmentFileDataMapper;
import ru.croc.univer.file_processor.mapper.FacultyFileDataMapper;
import ru.croc.univer.file_processor.mapper.StudentFileDataMapper;
import ru.croc.univer.repository.DepartmentRepository;
import ru.croc.univer.repository.FacultyRepository;
import ru.croc.univer.repository.StudentRepository;
import ru.croc.univer.repository.filter.DepartmentFilterType;
import ru.croc.univer.repository.filter.Filter;
import ru.croc.univer.repository.filter.StudentFilterType;
import ru.croc.univer.validation.ValidationResult;
import ru.croc.univer.validation.student.AddStudentValidator;
import ru.croc.univer.validation.student.AdmissionYearStudentValidator;
import ru.croc.univer.validation.student.AgeStudentValidator;
import ru.croc.univer.validation.student.DepartmentCodeStudentValidator;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        final var studentFileDataMapper = new StudentFileDataMapper();
        final var facultyFileDataMapper = new FacultyFileDataMapper();
        final var departmentFileDataMapper = new DepartmentFileDataMapper();
        try (
            var studentFP = new StudentFileProcessor(studentFileDataMapper);
            var depFp = new DepartmentFileProcessor(departmentFileDataMapper);
            var facFP = new FacultyFileProcessor(facultyFileDataMapper);
            var scanner = new Scanner(System.in);
        ) {
            final var faculties = facFP.readFileData();
            final var departments = depFp.readFileData();
            final var students = studentFP.readFileData();
            final var facultyRepository = new FacultyRepository(faculties);
            final var departmentRepository = new DepartmentRepository(departments);
            final var studentRepository = new StudentRepository(students, departmentRepository);
            final AddStudentValidator studentValidator = initStudentValidator(departments);

            final PrintStream out = System.out;
            out: while (true) {
                out.printf("Добрый день!\nНа сегодняшний день %s в университете учатся: %s на %s направлений.\n", DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now()), students.size(), departments.size());
                out.println("Выберите необходимое действие:");
                out.println("1 - Поиск информации");
                out.println("2 - Добавление информации");
                out.println("3 - Выход");

                int option = scanner.nextInt();
                switch (option) {
                    case 1: {
                        out.println("Что вас интересует?");
                        out.println("1.1 - Поиск студентов по году поступления");
                        out.println("1.2 - Вывод всех студентов, обучающихся на факультете");
                        out.println("1.3 - Вывод всех студентов, обучающихся по программе обучения");
                        out.println("1.4 - Поиск студентов по фамилии");
                        out.println("1.5 - Назад");

                        String option1 = scanner.next();

                        switch (option1) {
                            case "1.1": {
                                out.println("Введите интересующий год поступления в формате YYYY:");
                                final String admissionYear = scanner.next();
                                final List<Student> filteredStudents = studentRepository.findByFilter(new Filter(admissionYear, StudentFilterType.ADMISSION_YEAR));
                                if (filteredStudents.isEmpty()) {
                                    out.printf("На найдено студентов, поступивших в %s году\n", admissionYear);
                                    continue;
                                }
                                filteredStudents.forEach(out::println);
                                continue out;
                            }
                            case "1.3": {
                                out.println("Введите интересующую программу обучения (BACHELOR, MASTER, SPECIALIST):");
                                final String program = scanner.next();
                                final List<Student> filteredStudents = studentRepository.findByFilter(new Filter(program, StudentFilterType.PROGRAM));
                                if (filteredStudents.isEmpty()) {
                                    out.printf("На найдено студентов, обучающихся по %s программе обучения\n", program);
                                    continue;
                                }
                                filteredStudents.forEach(out::println);
                                continue out;
                            }
                            default: {
                                continue out;
                            }
                        }
                    }
                    case 2: {
                        out.println("Что вас интересует?");
                        out.println("2.1 - Добавление нового студента");
                        out.println("2.2 - Добавление новой кафедры");
                        out.println("2.3 - Назад");

                        String option2 = scanner.next();

                        switch (option2) {
                            case "2.1": {
                                out.println("Введите данные нового студента в формате LAST_NAME,FIRST_NAME,MIDDLE_NAME,AGE,DEPARTMENT_CODE,STUDY_YEAR,ADMISSION_YEAR");
                                final String studentData = scanner.next();
                                final String[] parts = studentData.split(",");
                                final AddStudentDto studentDto = new AddStudentDto(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
                                final ValidationResult validationResult = studentValidator.validate(studentDto);
                                if (!validationResult.valid()) {
                                    out.printf("Введенные вами данные не являются корректными. Атрибут %s не соответствует описанию.\n", validationResult.errorOnAttribute());
                                    continue out;
                                }
                                List<Department> foundDepartments = departmentRepository.findByFilter(new Filter(studentDto.departmentCode(), DepartmentFilterType.CODE));
                                if (foundDepartments.isEmpty()) {
                                    out.printf("Введенные вами данные не являются корректными. Кафедры с кодом: %s не существует.\n", studentDto.departmentCode());
                                    continue out;
                                }
                                final int depId = foundDepartments.get(0).id();

                                final Student student = new Student(
                                    ThreadLocalRandom.current().nextInt(100000),
                                    studentDto.lastName(),
                                    studentDto.firstName(),
                                    studentDto.middleName(),
                                    Integer.parseInt(studentDto.age()),
                                    depId,
                                    Integer.parseInt(studentDto.studyYear()),
                                    MessageFormat.format("{0,number,000}_{1,number,000}/{2}", ThreadLocalRandom.current().nextInt(999), genStudentNum(), studentDto.admissionYear())
                                );
                                studentFP.writeLine(student);
                                studentRepository.addEntity(student);
                                continue out;
                            }
                            case "2.2": {
                                out.println("Введите интересующую программу обучения (BACHELOR, MASTER, SPECIALIST):");
                                final String program = scanner.next();
                                final List<Student> filteredStudents = studentRepository.findByFilter(new Filter(program, StudentFilterType.PROGRAM));
                                if (filteredStudents.isEmpty()) {
                                    out.printf("На найдено студентов, обучающихся по %s программе обучения\n", program);
                                    continue;
                                }
                                filteredStudents.forEach(out::println);
                                continue out;
                            }
                            default: {
                                continue out;
                            }
                        }
                    }
                    case 3: {
                        break out;
                    }
                }
            }


        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static AddStudentValidator initStudentValidator(final List<Department> departments) {
        var ageValidator = new AgeStudentValidator();
        var admissionYeaValidator = new AdmissionYearStudentValidator();
        var depCodeValidator = new DepartmentCodeStudentValidator(departments);

        return new AddStudentValidator(List.of(ageValidator, admissionYeaValidator, depCodeValidator));
    }

    public static int genStudentNum() {
        while (true) {
            int genInt = ThreadLocalRandom.current().nextInt(999);
            if (genInt % 2 == 0 && genInt % 5 != 0 && genInt % 50 != 0) {
                return genInt;
            }
        }
    }
}
