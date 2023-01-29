package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.TaskRepositoryInterface;

import java.util.List;
import java.util.Optional;

import static ru.job4j.todo.util.TimeZoneUser.setTimeZone;

/**
 * класс TaskService реализует интерфейс TaskServiceInterface
 * Класс реализует бизнесс логику хранилища заданий
 */
@Service
@AllArgsConstructor
public class TaskService implements TaskServiceInterface {
    private TaskRepositoryInterface taskRepository;
    private PriorityServiceInterface priorityService;

    /**
     * метод добавляет задание в хранилище
     * @param task - задание
     * @return - Optional с добавленным заданием
     */
    @Override
    public Optional<Task> add(Task task) {
        if (priorityService.findById(task.getPriority().getId()).isEmpty()) {
            return Optional.empty();
        }
        return taskRepository.add(task);
    }

    /**
     * метод обновляет задание в хранилище
     * @param task - задание
     * @return - статус операции
     */
    @Override
    public boolean update(Task task) {
        if (priorityService.findById(task.getPriority().getId()).isEmpty()) {
            return false;
        }
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
    public List<Task> findAll(User user) {
        List<Task> tasks = taskRepository.findAll();
        tasks.forEach(e -> setTimeZone(user, e));
        return tasks;
    }

    /**
     * метод поиска задания в хранилище по id
     * @param id - уникальный индификатор задания
     * @return - Optional с результатом поиска задания
     */
    @Override
    public Optional<Task> findById(int id, User user) {
        Optional<Task> opTask = taskRepository.findById(id);
        opTask.ifPresent(task -> setTimeZone(user, task));
        return opTask;
    }

    /**
     * метод поиска заданий со статусом done
     * @param done - статус у задания
     * @return - список заданий
     */
    @Override
    public List<Task> findByDone(boolean done, User user) {
        List<Task> tasks = taskRepository.findByDone(done);
        tasks.forEach(e -> setTimeZone(user, e));
        return tasks;
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
