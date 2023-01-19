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

    @Override
    public Optional<Task> add(Task task) {
        return taskRepository.add(task);
    }

    @Override
    public boolean update(Task task) {
        return taskRepository.update(task);
    }

    @Override
    public boolean delete(int id) {
        return taskRepository.delete(id);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> findDoneTrue() {
        return taskRepository.findDoneTrue();
    }

    @Override
    public List<Task> findDoneFalse() {
        return taskRepository.findDoneFalse();
    }
}
