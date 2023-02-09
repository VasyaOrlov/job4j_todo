package ru.job4j.todo.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserServiceInterface;
import ru.job4j.todo.util.TimeZoneUser;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Test
    public void whenRegistration() {
        UserServiceInterface userService = mock(UserServiceInterface.class);
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserController userController = new UserController(userService);
        User user = new User(0, "name", "login", "pass", null);

        String rsl = userController.registration(model, session);
        when(session.getAttribute("user")).thenReturn(new User());

        verify(model).addAttribute("user", user);
        verify(model).addAttribute("userZones", TimeZoneUser.getZone());
        assertThat(rsl).isEqualTo("users/registration");
    }

    @Test
    public void whenAddUser() {
        UserServiceInterface userService = mock(UserServiceInterface.class);
        HttpSession session = mock(HttpSession.class);
        UserController userController = new UserController(userService);
        User user = new User(0, "name", "login", "pass", null);

        when(userService.add(Mockito.any(User.class))).thenReturn(Optional.of(user));
        String rsl = userController.addUser(user, session);

        verify(userService).add(user);
        assertThat(rsl).isEqualTo("redirect:/tasks/showMessage");
    }

    @Test
    public void whenAuthorization() {
        UserServiceInterface userService = mock(UserServiceInterface.class);
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserController userController = new UserController(userService);
        User user = new User(0, "name", "login", "pass", null);

        when(session.getAttribute("user")).thenReturn(new User());
        String rsl = userController.authorization(model, session);

        verify(model).addAttribute("user", user);
        assertThat(rsl).isEqualTo("users/authorization");
    }

    @Test
    public void whenLogin() {
        UserServiceInterface userService = mock(UserServiceInterface.class);
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserController userController = new UserController(userService);
        User user = new User(0, "name", "login", "pass", null);

        when(userService.findByLoginAndPassword(Mockito.any(String.class), Mockito.any(String.class)))
                .thenReturn(Optional.of(user));
        String rsl = userController.login(user, model, session);

        verify(session).setAttribute("user", user);
        assertThat(rsl).isEqualTo("redirect:/tasks/all");
    }

    @Test
    public void whenLogout() {
        HttpSession session = mock(HttpSession.class);
        UserServiceInterface userService = mock(UserServiceInterface.class);
        UserController userController = new UserController(userService);

        String rsl = userController.logout(session);

        verify(session).invalidate();
        assertThat(rsl).isEqualTo("redirect:/users/authorization");
    }
}