package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserServiceInterface;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static ru.job4j.todo.util.GetUser.getUser;

@Controller
@ThreadSafe
@AllArgsConstructor
public class UserController {

    private final UserServiceInterface userService;

    @GetMapping("/registration")
    public String registration(Model model, HttpSession httpSession) {
        getUser(model, httpSession);
        return "registration";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user) {
        Optional<User> rsl = userService.add(user);
        if (rsl.isEmpty()) {
            return "redirect:/addUserFail";
        }
        return "redirect:/addUserSuccess";
    }

    @GetMapping("/addUserFail")
    public String addUserFail(Model model, HttpSession httpSession) {
        getUser(model, httpSession);
        return "addUserFail";
    }

    @GetMapping("/addUserSuccess")
    public String addUserSuccess(Model model, HttpSession httpSession) {
        getUser(model, httpSession);
        return "addUserSuccess";
    }

    @GetMapping("/authorization")
    public String authorization(Model model, HttpSession httpSession) {
        getUser(model, httpSession);
        return "authorization";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model, HttpSession httpSession) {
        Optional<User> temp = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (temp.isEmpty()) {
            model.addAttribute("error", "Логин или пароль введены неверно!");
            return "authorization";
        }
        httpSession.setAttribute("user", temp.get());
        return "redirect:/allTask";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/login";
    }
}
