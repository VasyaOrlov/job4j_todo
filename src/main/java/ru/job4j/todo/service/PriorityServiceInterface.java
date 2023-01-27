package ru.job4j.todo.service;

import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Optional;

/**
 * интерфейс описывает логику работы хранилища приоритетов
 */
public interface PriorityServiceInterface {
    List<Priority> findAll();
    Optional<Priority> findById(int id);
}
