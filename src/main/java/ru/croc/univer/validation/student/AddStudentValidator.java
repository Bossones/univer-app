package ru.croc.univer.validation.student;

import ru.croc.univer.dto.AddStudentDto;
import ru.croc.univer.validation.ValidationResult;
import ru.croc.univer.validation.Validator;

import java.util.List;

public class AddStudentValidator implements Validator<AddStudentDto> {

    private final List<Validator<AddStudentDto>> validators;

    public AddStudentValidator(List<Validator<AddStudentDto>> validators) {
        this.validators = validators;
    }

    @Override
    public ValidationResult validate(AddStudentDto entity) {
        for (var validator : validators) {
            ValidationResult result = validator.validate(entity);
            if (!result.valid()) {
                return result;
            }
        }
        return new ValidationResult(true, null);
    }
}
