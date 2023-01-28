package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import java.util.List;
import java.util.Map;

/**
 * Класс CategoryRepository реализует интерфейс CategoryRepositoryInterface
 * Класс реализует хранилище категорий в базе данных
 */
@Repository
@ThreadSafe
@AllArgsConstructor
public class CategoryRepository implements CategoryRepositoryInterface {

    private static final String FIND_ALL = "from Category";
    private static final String FIND_BY_ID = "from Category where id in :fListId";
    private CRUDRepositoryInterface crudRepository;

    /**
     * находит все категории в базе данных
     * @return - список категорий
     */
    @Override
    public List<Category> findAll() {
        return crudRepository.list(FIND_ALL, Category.class);
    }

    /**
     * находит категории с id входящим в listId
     * @param listId - список id(индификаторов)
     * @return - список категорий
     */
    @Override
    public List<Category> findByID(List<Integer> listId) {
        return crudRepository.list(FIND_BY_ID, Category.class, Map.of("fListId", listId));
    }
}
