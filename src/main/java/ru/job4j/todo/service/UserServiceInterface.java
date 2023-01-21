package ru.job4j.todo.service;

import ru.job4j.todo.model.User;
import java.util.Optional;

/**
 * UserServiceInterface - интерфейс описывает логику работы хранилища пользователей
 */
public interface UserServiceInterface {
    Optional<User> add(User user);
    Optional<User> findByLoginAndPassword(String login, String password);
}
