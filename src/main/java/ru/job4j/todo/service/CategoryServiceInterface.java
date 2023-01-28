package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;
import java.util.List;

/**
 * CategoryServiceInterface - интерфейс описывает логику работу хранилища категорий
 */
public interface CategoryServiceInterface {
    List<Category> findAll();
    List<Category> findByID(List<Integer> listId);
}
