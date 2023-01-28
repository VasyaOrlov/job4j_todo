package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.PriorityRepositoryInterface;

import java.util.List;
import java.util.Optional;

@Service
@ThreadSafe
@AllArgsConstructor
public class PriorityService implements PriorityServiceInterface {

    private PriorityRepositoryInterface priorityRepository;

    /**
     * находит все приоритеты в хранилище приоритетов
     * @return - список приоритетов
     */
    @Override
    public List<Priority> findAll() {
        return priorityRepository.findAll();
    }

    /**
     * находит приоритет по id в хранилище приоритетов
     * @param id - индификатор
     * @return Optional с найденным приоритетом
     */
    @Override
    public Optional<Priority> findById(int id) {
        return priorityRepository.findById(id);
    }
}
