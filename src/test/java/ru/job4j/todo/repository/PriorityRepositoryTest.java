package ru.job4j.todo.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.todo.model.Priority;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class PriorityRepositoryTest {

    private final SessionFactory sf = new MetadataSources(
            new StandardServiceRegistryBuilder().configure().build()
    ).buildMetadata().buildSessionFactory();
    private final CRUDRepositoryInterface crudRepository =
            new CRUDRepository(sf);
    private final PriorityRepositoryInterface priorityRepository =
            new PriorityRepository(crudRepository);

    @Test
    public void whenFindById() {
        Optional<Priority> rsl = priorityRepository.findById(1);
        assertThat(rsl.get().getName()).isEqualTo("срочно");
    }

    @Test
    public void whenFindAll() {
        List<String> rsl = priorityRepository.findAll()
                .stream()
                .map(Priority::getName)
                .toList();
        List<String> expect = List.of("срочно", "обычный");
        assertThat(rsl).isEqualTo(expect);
    }
}