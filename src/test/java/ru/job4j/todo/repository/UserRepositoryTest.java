package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.todo.model.User;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;

class UserRepositoryTest {

    private final SessionFactory sf = new MetadataSources(
            new StandardServiceRegistryBuilder().configure().build()
    ).buildMetadata().buildSessionFactory();
    private final CRUDRepositoryInterface crudRepository = new CRUDRepository(sf);
    private final UserRepositoryInterface userRepository = new UserRepository(crudRepository);

    @AfterEach
    public void clear() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete User").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void whenAddUser() {
        User user = new User(0, "name", "login", "pass", null);
        Optional<User> rsl = userRepository.add(user);
        assertThat(rsl.get().getName()).isEqualTo("name");
    }

    @Test
    public void whenFindAll() {
        User user1 = new User(0, "name1", "login1", "pass", null);
        userRepository.add(user1);
        User user2 = new User(0, "name2", "login2", "pass", null);
        userRepository.add(user2);

        Optional<User> rsl = userRepository.findByLoginAndPassword("login2", "pass");
        Optional<User> rsl2 = userRepository.findByLoginAndPassword("log", "pass");

        assertThat(rsl.get().getName()).isEqualTo("name2");
        assertThat(rsl2.isEmpty()).isTrue();
    }
}