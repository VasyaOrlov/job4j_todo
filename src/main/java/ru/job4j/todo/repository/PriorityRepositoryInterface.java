package ru.job4j.todo.repository;

import ru.job4j.todo.model.Priority;
import java.util.List;
import java.util.Optional;

/**
 * PriorityRepositoryInterface - интерфейс хранилища объектов класса Priority
 */
public interface PriorityRepositoryInterface {
    List<Priority> findAll();
    Optional<Priority> findById(int id);
}
