package ru.croc.univer.validation.student;

import ru.croc.univer.dto.AddStudentDto;
import ru.croc.univer.validation.ValidationResult;
import ru.croc.univer.validation.Validator;

public class AdmissionYearStudentValidator implements Validator<AddStudentDto> {

    @Override
    public ValidationResult validate(AddStudentDto entity) {
        try {
            final int admissionYear = Integer.parseInt(entity.admissionYear());
            if (admissionYear < 2014 || admissionYear > 2024) {
                return new ValidationResult(false, "ADMISSION_YEAR");
            }
        } catch (NumberFormatException numberFormatException) {
            return new ValidationResult(false, "ADMISSION_YEAR");
        }
        return new ValidationResult(true, null);
    }
}
