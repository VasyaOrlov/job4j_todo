package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
import java.util.Optional;

/**
 * Класс UserRepository реализует интерфейс UserRepositoryInterface
 * Класс реализует хранилище пользователей в базе данных
 */
@Repository
@ThreadSafe
@AllArgsConstructor
public class UserRepository implements UserRepositoryInterface {

    private final SessionFactory sf;
    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class.getName());
    private static final String FIND_BY_LOGIN_PASSWORD = "from User where login = :fLogin and password = :fPassword";

    /**
     * метод добавляет пользователя в базу данных
     * @param user - пользователь
     * @return - Optional  с добавленным пользователем
     */
    @Override
    public Optional<User> add(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            LOG.error("Ошибка добавления нового пользователя" + e);
            return Optional.empty();
        }
        return Optional.of(user);
    }

    /**
     * метод находит пользователя по логину и паролю
     * @param login - логин
     * @param password - пароль
     * @return - Optional с пользователем у которого соответствуют логин и пароль
     */
    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Session session = sf.openSession();
        session.beginTransaction();
        Optional<User> user = session.createQuery(FIND_BY_LOGIN_PASSWORD, User.class)
                .setParameter("fLogin", login)
                .setParameter("fPassword", password)
                .uniqueResultOptional();
        session.getTransaction().commit();
        session.close();
        return user;
    }
}
