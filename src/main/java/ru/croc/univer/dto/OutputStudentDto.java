package ru.croc.univer.dto;

import java.text.MessageFormat;

public record OutputStudentDto(int id, String lastName, String firstName, String middleName, int age, String departmentName, String facultyName, int studyYear, String code) {

    @Override
    public String toString() {
        return MessageFormat.format(
            "{0},{1},{2},{3},{4},{5},{6},{7},{8}",
            id,
            lastName,
            firstName,
            middleName,
            age,
            departmentName,
            facultyName,
            studyYear,
            code
        );
    }
}
