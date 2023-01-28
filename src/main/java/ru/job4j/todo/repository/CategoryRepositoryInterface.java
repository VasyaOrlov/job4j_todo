package ru.job4j.todo.repository;

import ru.job4j.todo.model.Category;

import java.util.List;

/**
 * CategoryRepositoryInterface - интерфейс хранилища категорий
 */
public interface CategoryRepositoryInterface {
    List<Category> findAll();
    List<Category> findByID(List<Integer> listId);
}