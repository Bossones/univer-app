package ru.croc.univer.entity;

public record Faculty(int id, String name, String code) {

    public Faculty {
        name = name.trim();
        code = code.trim();
    }
}
