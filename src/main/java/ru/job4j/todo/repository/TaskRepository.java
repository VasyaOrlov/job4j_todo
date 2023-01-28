package ru.job4j.todo.repository;

import net.jcip.annotations.ThreadSafe;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Класс TaskRepository реализует интерфейс TaskRepositoryInterface
 * Класс реализует хранилище заданий в базе данных
 */
@Repository
@ThreadSafe
@AllArgsConstructor
public class TaskRepository implements TaskRepositoryInterface {

    private CRUDRepositoryInterface crudRepository;
    private static final Logger LOG = LoggerFactory.getLogger(TaskRepository.class.getName());
    private static final String DELETE = "delete Task where id =:fId";
    private static final String FIND_BY_ID = "from Task f JOIN FETCH f.priority "
            + "JOIN FETCH f.categories where f.id = :fId";
    private static final String FIND_ALL = "from Task f JOIN FETCH f.priority "
            + "JOIN FETCH f.categories";
    private static final String FIND_DONE = "from Task f JOIN FETCH f.priority "
            + "JOIN FETCH f.categories where done = :fDone";
    private static final String DONE_TRUE = "update Task set done = true where id = :fId";

    /**
     * метод добавляет задание в базу данных
     * @param task - задание
     * @return возвращает добавленное задание
     */
    @Override
    public Optional<Task> add(Task task) {
        Optional<Task> rsl = Optional.empty();
        try {
            crudRepository.optional(session -> session.save(task));
            rsl = Optional.of(task);
        } catch (Exception e) {
            LOG.error("Ошибка добавления нового задания" + e);
        }
        return rsl;
    }

    /**
     * метод обновляет задание в базе данных
     * @param task - задание
     * @return - возвращает статус выполнения операции
     */
    @Override
    public boolean update(Task task) {
        boolean rsl = false;
        try {
            rsl = crudRepository.total(session -> session.merge(task).equals(task));
        } catch (Exception e) {
            LOG.error("Ошибка изменения задачи: " + e);
        }
        return rsl;
    }

    /**
     * метод удаляет задание из базы данных
     * @param id - индификатор задания
     * @return - возвращает статус выполнения операции
     */
    @Override
    public boolean delete(int id) {
        boolean rsl = false;
        try {
            rsl = crudRepository.total(DELETE, Map.of("fId", id));
        } catch (Exception e) {
            LOG.error("Ошибка удаления задачи: " + e);
        }
        return rsl;
    }

    /**
     * метод находит все задания в базе данных
     * @return список всех заданий
     */
    @Override
    public List<Task> findAll() {
        return crudRepository.list(FIND_ALL, Task.class);
    }

    /**
     * метод находит задание с указанным id
     * @param id - индификатор задания
     * @return - Optional с заявкой
     */
    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Task.class, Map.of("fId", id));
    }

    /**
     * метод находит все задания со статусом done
     * @param done - статус задания
     * @return - список заявок
     */
    @Override
    public List<Task> findByDone(boolean done) {
        return crudRepository.list(FIND_DONE, Task.class, Map.of("fDone", done));
    }

    /**
     * метод у задания с id статус переводит на выполнено
     * @param id - индификатор задания
     * @return - результат операции
     */
    @Override
    public boolean doneTrue(int id) {
        boolean rsl = false;
        try {
            rsl = crudRepository.total(DONE_TRUE, Map.of("fId", id));
        } catch (Exception e) {
            LOG.error("Ошибка выполнения задания: " + e);
        }
        return rsl;
    }
}