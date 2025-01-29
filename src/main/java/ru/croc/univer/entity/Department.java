package ru.croc.univer.entity;

import ru.croc.univer.enums.Program;

public record Department(int id, String name, int studyDuration, int faculty, Program program, String code) {

    public Department {
        name = name.trim();
        code = code.trim();
    }
}
