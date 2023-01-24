package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserServiceInterface;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static ru.job4j.todo.util.GetUser.getUser;

@Controller
@ThreadSafe
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserServiceInterface userService;

    /**
     * метод возвращает вид с регистрацией пользователя
     * @param model - модель данных
     * @param httpSession - объект связанный с работой пользователя
     * @return - возвращает вид с регистрацией пользователя
     */
    @GetMapping("/registration")
    public String registration(Model model, HttpSession httpSession) {
        getUser(model, httpSession);
        return "users/registration";
    }

    /**
     * метод добавляет пользователя в хранилище и перенаправляет на вид с результатом добавления
     * @param user - пользователь
     * @param httpSession - объект связанный с работой пользователя
     * @return - вид с результатом
     */
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user, HttpSession httpSession) {
        Optional<User> rsl = userService.add(user);
        if (rsl.isEmpty()) {
            httpSession.setAttribute("message", "Ошибка при регистрации пользователя!");
            return "redirect:/tasks/showMessage";
        }
        httpSession.setAttribute("message", "Пользователь зарегистрирован!");
        return "redirect:/tasks/showMessage";
    }

    /**
     * метод возвращает вид с авторизацией пользователя
     * @param model - модель данных
     * @param httpSession - объект связанный с работой пользователя
     * @return - возвращает вид с авторизацией пользователя
     */
    @GetMapping("/authorization")
    public String authorization(Model model, HttpSession httpSession) {
        getUser(model, httpSession);
        return "users/authorization";
    }

    /**
     * метод добавляет пользователя в объект httpSession
     * @param user - пользователь
     * @param model - модель данных
     * @param httpSession - объект связанный с работой пользователя
     * @return - возвращает вид авторизации или список всех заданий
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model, HttpSession httpSession) {
        Optional<User> temp = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (temp.isEmpty()) {
            model.addAttribute("error", "Логин или пароль введены неверно!");
            return "users/authorization";
        }
        httpSession.setAttribute("user", temp.get());
        return "redirect:/tasks/all";
    }

    /**
     * метод завершает работу объекта httpSession
     * @param httpSession - объект связанный с работой пользователя
     * @return - перенаправляет на вид авторизации
     */
    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/users/authorization";
    }
}
