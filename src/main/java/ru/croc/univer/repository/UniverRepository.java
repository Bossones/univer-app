package ru.croc.univer.repository;

import ru.croc.univer.repository.filter.Filter;

import java.util.List;

public interface UniverRepository<T> {

    List<T> findByFilter(final Filter filter);

    void addEntity(final T entity);
}
