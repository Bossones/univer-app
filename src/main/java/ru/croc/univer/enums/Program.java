package ru.croc.univer.enums;

import java.util.Optional;

public enum Program {

    BACHELOR,
    MASTER,
    SPECIALIST;

    public static Optional<Program> findByProgram(final String programName) {
        for (var program: Program.values()) {
            if (program.name().equalsIgnoreCase(programName)) {
                return Optional.of(program);
            }
        }
        return Optional.empty();
    }
}
