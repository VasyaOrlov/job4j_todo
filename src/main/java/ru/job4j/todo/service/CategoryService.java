package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepositoryInterface;
import java.util.List;

/**
 * Класс реализует интерфейс
 * Класс реализует бизнесс логику хранилища категорий
 */
@Service
@ThreadSafe
@AllArgsConstructor
public class CategoryService implements CategoryServiceInterface {

    private CategoryRepositoryInterface categoryRepository;

    /**
     * метод находит все категории в хранилище
     * @return список категорий
     */
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    /**
     * находит категории с id входящим в listId
     * @param listId - список id(индификаторов)
     * @return - список категорий
     */
    @Override
    public List<Category> findByID(List<Integer> listId) {
        return categoryRepository.findByID(listId);
    }
}