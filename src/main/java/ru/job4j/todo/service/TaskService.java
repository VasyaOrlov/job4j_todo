package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepositoryInterface;

import java.util.List;
import java.util.Optional;

/**
 * класс TaskService реализует интерфейс TaskServiceInterface
 * Класс реализует бизнесс логику хранилища заданий
 */
@Service
@AllArgsConstructor
public class TaskService implements TaskServiceInterface {
    private TaskRepositoryInterface taskRepository;

    /**
     * метод добавляет задание в хранилище
     * @param task - задание
     * @return - Optional с добавленным заданием
     */
    @Override
    public Optional<Task> add(Task task) {
        return taskRepository.add(task);
    }

    /**
     * метод обновляет задание в хранилище
     * @param task - задание
     * @return - статус операции
     */
    @Override
    public boolean update(Task task) {
        return taskRepository.update(task);
    }

    /**
     * метод удаляет задание из хранилища по id
     * @param id - индификатор задания
     * @return - статус операции
     */
    @Override
    public boolean delete(int id) {
        return taskRepository.delete(id);
    }

    /**
     * метод поиска всех заданий в хранилище
     * @return - список заданий
     */
    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    /**
     * метод поиска задания в хранилище по id
     * @param id - уникальный индификатор задания
     * @return - Optional с результатом поиска задания
     */
    @Override
    public Optional<Task> findById(int id) {
        return taskRepository.findById(id);
    }

    /**
     * метод поиска заданий со статусом done
     * @param done - статус у задания
     * @return - список заданий
     */
    @Override
    public List<Task> findByDone(boolean done) {
        return taskRepository.findByDone(done);
    }

    /**
     * метод у задания с id статус переводит на выполнено
     * @param id - индификатор задания
     * @return - результат операции
     */
    @Override
    public boolean doneTrue(int id) {
        return taskRepository.doneTrue(id);
    }
}
