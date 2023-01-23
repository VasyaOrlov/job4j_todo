package ru.job4j.todo.repository;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import java.util.List;
import java.util.Optional;

/**
 * Класс TaskRepository реализует интерфейс TaskRepositoryInterface
 * Класс реализует хранилище заданий в базе данных
 */
@Repository
@ThreadSafe
@AllArgsConstructor
public class TaskRepository implements TaskRepositoryInterface {
    private final SessionFactory sf;
    private static final Logger LOG = LoggerFactory.getLogger(TaskRepository.class.getName());
    private static final String UPDATE =
            "update Task set name = :fName, description = :fDesc, "
                    + "created = :fCreated, done = :fDone where id = :fId";

    private static final String DELETE = "delete Task where id =:fId";
    private static final String FIND_ALL = "from Task";
    private static final String FIND_DONE = "from Task where done = :fDone";
    private static final String DONE_TRUE = "update Task set done = true where id = :fId";

    /**
     * метод добавляет задание в базу данных
     * @param task - задание
     * @return возвращает добавленное задание
     */
    @Override
    public Optional<Task> add(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Ошибка добавления задачи: " + e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return Optional.of(task);
    }

    /**
     * метод обновляет задание в базе данных
     * @param task - задание
     * @return - возвращает статус выполнения операции
     */
    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        boolean rsl = false;
        try {
            session.beginTransaction();
            rsl = session.createQuery(UPDATE)
                    .setParameter("fName", task.getName())
                    .setParameter("fDesc", task.getDescription())
                    .setParameter("fCreated", task.getCreated())
                    .setParameter("fDone", task.isDone())
                    .setParameter("fId", task.getId())
                    .executeUpdate() != 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Ошибка изменения задачи: " + e);
            session.getTransaction().rollback();
        } finally {
            session.close();
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
        Session session = sf.openSession();
        boolean rsl = false;
        try {
            session.beginTransaction();
            rsl = session.createQuery(DELETE)
                    .setParameter("fId", id)
                    .executeUpdate() != 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Ошибка изменения задачи: " + e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    /**
     * метод находит все задания в базе данных
     * @return список всех заданий
     */
    @Override
    public List<Task> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task> rsl = session.createQuery(FIND_ALL, Task.class).list();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    /**
     * метод находит задание с указанным id
     * @param id - индификатор задания
     * @return - Optional с заявкой
     */
    @Override
    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Optional<Task> rsl = Optional.ofNullable(session.get(Task.class, id));
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    /**
     * метод находит все задания со статусом done
     * @param done - статус задания
     * @return - список заявок
     */
    @Override
    public List<Task> findByDone(boolean done) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task> rsl = session.createQuery(FIND_DONE, Task.class)
                .setParameter("fDone", done)
                .list();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    /**
     * метод у задания с id статус переводит на выполнено
     * @param id - индификатор задания
     * @return - результат операции
     */
    @Override
    public boolean doneTrue(int id) {
        Session session = sf.openSession();
        boolean rsl = false;
        try {
            session.beginTransaction();
            rsl = session.createQuery(DONE_TRUE)
                    .setParameter("fId", id)
                    .executeUpdate() != 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Ошибка выполнения задания: " + e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }
}