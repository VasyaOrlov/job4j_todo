package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
import java.util.Map;
import java.util.Optional;

/**
 * Класс UserRepository реализует интерфейс UserRepositoryInterface
 * Класс реализует хранилище пользователей в базе данных
 */
@Repository
@ThreadSafe
@AllArgsConstructor
public class UserRepository implements UserRepositoryInterface {

    private final CRUDRepositoryInterface crudRepository;
    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class.getName());
    private static final String FIND_BY_LOGIN_PASSWORD = "from User where login = :fLogin and password = :fPassword";

    /**
     * метод добавляет пользователя в базу данных
     * @param user - пользователь
     * @return - Optional  с добавленным пользователем
     */
    @Override
    public Optional<User> add(User user) {
        Optional<User> rsl = Optional.empty();
        try {
            crudRepository.optional(session -> session.save(user));
            rsl = Optional.of(user);
        } catch (Exception e) {
            LOG.error("Ошибка добавления нового пользователя" + e);
            System.out.println("Ошибка добавления нового пользователя");
        }
        return rsl;
    }

    /**
     * метод находит пользователя по логину и паролю
     * @param login - логин
     * @param password - пароль
     * @return - Optional с пользователем у которого соответствуют логин и пароль
     */
    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(FIND_BY_LOGIN_PASSWORD,
                User.class,
                Map.of("fLogin", login, "fPassword", password));
    }
}
