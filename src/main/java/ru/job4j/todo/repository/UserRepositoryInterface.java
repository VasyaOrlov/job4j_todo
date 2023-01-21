package ru.job4j.todo.repository;

import ru.job4j.todo.model.User;
import java.util.Optional;

/**
 * UserRepositoryInterface - интерфейс хранилища пользователей
 */
public interface UserRepositoryInterface {

    Optional<User> add(User user);
    Optional<User> findByLoginAndPassword(String login, String password);
}
