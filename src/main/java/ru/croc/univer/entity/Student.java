package ru.croc.univer.entity;

public record Student(int id, String lastName, String firstName, String middleName, int age, int department, int studyYear, String code) {

    public Student {
        lastName = lastName.trim();
        firstName = firstName.trim();
        middleName = middleName.trim();
        code = code.trim();
    }
}
