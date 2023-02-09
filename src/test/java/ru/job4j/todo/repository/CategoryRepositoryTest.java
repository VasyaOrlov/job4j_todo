package ru.job4j.todo.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.todo.model.Category;
import static org.assertj.core.api.Assertions.*;
import java.util.List;

class CategoryRepositoryTest {

    private final SessionFactory sf = new MetadataSources(
            new StandardServiceRegistryBuilder().configure().build()
    ).buildMetadata().buildSessionFactory();
    private final CRUDRepositoryInterface crudRepository = new CRUDRepository(sf);
    private final CategoryRepositoryInterface categoryRepository = new CategoryRepository(crudRepository);

    @Test
    public void whenFindByID() {
        List<Category> rsl = categoryRepository.findByID(List.of(1));
        System.out.println(rsl);
        assertThat(rsl.get(0).getName()).isEqualTo("Выполнить");
    }

    @Test
    public void whenFindAll() {
        List<String> rsl = categoryRepository.findAll()
                .stream()
                .map(Category::getName)
                .toList();
        List<String> expect = List.of("Выполнить", "Исправить", "Протестировать");
        assertThat(rsl).isEqualTo(expect);
    }
}