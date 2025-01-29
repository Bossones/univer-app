package ru.croc.univer.repository;

import ru.croc.univer.entity.Faculty;
import ru.croc.univer.repository.filter.Filter;

import java.util.List;

public class FacultyRepository implements UniverRepository<Faculty> {

    private final List<Faculty> faculties;

    public FacultyRepository(List<Faculty> faculties) {
        this.faculties = faculties;
    }

    @Override
    public List<Faculty> findByFilter(Filter filter) {
        return faculties;
    }

    @Override
    public void addEntity(Faculty entity) {
        faculties.add(entity);
    }
}
