package ru.croc.univer.validation;

public interface Validator<T> {

    ValidationResult validate(T entity);
}
