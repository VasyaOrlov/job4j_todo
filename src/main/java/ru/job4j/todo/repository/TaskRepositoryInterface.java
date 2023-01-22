package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

/**
 * интерфейс хранилища заданий
 */
public interface TaskRepositoryInterface {

    Optional<Task> add(Task task);
    boolean update(Task task);
    boolean delete(int id);
    List<Task> findAll();
    Optional<Task> findById(int id);
    List<Task> findByDone(boolean done);
    boolean doneTrue(int id);
}
