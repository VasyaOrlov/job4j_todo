package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskServiceInterface {
    Optional<Task> add(Task task);
    boolean update(Task task);
    boolean delete(int id);
    List<Task> findAll();
    Optional<Task> findById(int id);
    List<Task> findDoneTrue();
    List<Task> findDoneFalse();
}