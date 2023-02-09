package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class TaskRepositoryTest {

    private final SessionFactory sf = new MetadataSources(
            new StandardServiceRegistryBuilder().configure().build()
    ).buildMetadata().buildSessionFactory();
    private final CRUDRepositoryInterface crudRepository = new CRUDRepository(sf);
    private final TaskRepositoryInterface taskRepository = new TaskRepository(crudRepository);
    private final PriorityRepositoryInterface priorityRepository =
            new PriorityRepository(crudRepository);
    private final CategoryRepositoryInterface categoryRepository =
            new CategoryRepository(crudRepository);

    @AfterEach
    public void clear() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete Task").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void whenAdd() {
        Task task = new Task();
        task.setName("task");
        task.setDescription("desc");
        Optional<Task> rsl = taskRepository.add(task);
        assertThat(rsl.get().getName()).isEqualTo("task");
    }

    @Test
    public void whenUpdate() {
        Task task = new Task();
        task.setName("task");
        task.setDescription("desc");
        taskRepository.add(task);

        task.setName("update");
        boolean rsl = taskRepository.update(task);
        assertThat(rsl).isTrue();
    }

    @Test
    public void whenDelete() {
        Task task = new Task();
        task.setName("task");
        task.setDescription("desc");
        taskRepository.add(task);

        boolean rsl = taskRepository.delete(task.getId());
        assertThat(rsl).isTrue();
    }

    @Test
    public void whenFindAll() {
        Priority priority = priorityRepository.findById(1).orElse(new Priority());
        List<Category> categories = categoryRepository.findByID(List.of(1));

        Task task1 = new Task();
        task1.setName("task1");
        task1.setDescription("desc1");
        task1.setPriority(priority);
        task1.setCategories(categories);
        task1.setDescription("desc1");
        taskRepository.add(task1);

        Task task2 = new Task();
        task2.setName("task2");
        task2.setDescription("desc2");
        task2.setPriority(priority);
        task2.setCategories(categories);
        taskRepository.add(task2);

        List<Task> rsl = taskRepository.findAll();
        List<Task> expect = List.of(task1, task2);
        assertThat(rsl).isEqualTo(expect);
    }

    @Test
    public void whenFindByDone() {
        Priority priority = priorityRepository.findById(1).orElse(new Priority());
        List<Category> categories = categoryRepository.findByID(List.of(1));

        Task task1 = new Task();
        task1.setName("task1");
        task1.setDescription("desc1");
        task1.setPriority(priority);
        task1.setCategories(categories);
        task1.setDone(true);
        taskRepository.add(task1);

        Task task2 = new Task();
        task2.setName("task2");
        task2.setDescription("desc2");
        task2.setPriority(priority);
        task2.setCategories(categories);
        task2.setDone(false);
        taskRepository.add(task2);

        List<Task> rsl1 = taskRepository.findByDone(true);
        List<Task> expect1 = List.of(task1);
        List<Task> rsl2 = taskRepository.findByDone(false);
        List<Task> expect2 = List.of(task2);
        assertThat(rsl1).isEqualTo(expect1);
        assertThat(rsl2).isEqualTo(expect2);
    }

    @Test
    public void whenDineTrue() {
        Task task1 = new Task();
        task1.setName("task1");
        task1.setDescription("desc1");
        task1.setDone(false);
        taskRepository.add(task1);

        boolean rsl = taskRepository.doneTrue(task1.getId());
        assertThat(rsl).isTrue();
    }
}