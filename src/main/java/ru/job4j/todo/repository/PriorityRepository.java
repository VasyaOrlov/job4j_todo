package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;
import java.util.List;
import java.util.Optional;

/**
 * Класс PriorityRepository реализует интерфейс PriorityRepositoryInterface
 * Класс реализует хранилище приоритетов в базе данных
 */
@Repository
@ThreadSafe
@AllArgsConstructor
public class PriorityRepository implements PriorityRepositoryInterface {

    private static final String FIND_ALL = "from Priority";
    private CRUDRepositoryInterface crudRepository;

    /**
     * находит все приоритеты в базе данных
     * @return - список приоритетов
     */
    @Override
    public List<Priority> findAll() {
        return crudRepository.list(FIND_ALL, Priority.class);
    }

    /**
     * находит приоритет по id
     * @param id - индификатор
     * @return - Optional с найденным приоритетом
     */
    @Override
    public Optional<Priority> findById(int id) {
        return crudRepository.optional(Priority.class, id);
    }
}