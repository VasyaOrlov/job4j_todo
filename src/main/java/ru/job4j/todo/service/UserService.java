package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserRepositoryInterface;

import java.util.Optional;

/**
 * Класс UserService реализует интерфейс UserServiceInterface
 * Класс реализует бизнесс логику хранилища пользователей
 */
@Service
@ThreadSafe
@AllArgsConstructor
public class UserService implements UserServiceInterface {
    private final UserRepositoryInterface userRepository;

    /**
     * метод добавляет пользователя в хранилище userRepository
     * @param user - пользователь
     * @return - Optional с добавленным пользователем
     */
    @Override
    public Optional<User> add(User user) {
        return userRepository.add(user);
    }

    /**
     * метод ищет пользователя по логину и паролю в хранилище userRepository
     * @param login - логин
     * @param password - пароль
     * @return - Optional с пользователем у которого соответствуют логин и пароль
     */
    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password);
    }
}
