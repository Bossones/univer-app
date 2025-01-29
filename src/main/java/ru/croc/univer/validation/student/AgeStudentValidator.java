package ru.croc.univer.validation.student;

import ru.croc.univer.dto.AddStudentDto;
import ru.croc.univer.validation.ValidationResult;
import ru.croc.univer.validation.Validator;

public class AgeStudentValidator implements Validator<AddStudentDto> {

    @Override
    public ValidationResult validate(AddStudentDto entity) {
        try {
            int age = Integer.parseInt(entity.age());
            if (age < 16 || age > 40) {
                return new ValidationResult(false, "AGE");
            }
        } catch (NumberFormatException numberFormatException) {
            return new ValidationResult(false, "AGE");
        }
        return new ValidationResult(true, null);
    }
}
