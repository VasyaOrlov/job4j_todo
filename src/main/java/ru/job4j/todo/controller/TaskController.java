package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskServiceInterface;

import javax.servlet.http.HttpSession;

import static ru.job4j.todo.util.GetUser.getUser;

/**
 * TaskController - класс контроллер,
 * отвечает за работу с заданиями
 */
@Controller
@ThreadSafe
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private TaskServiceInterface taskService;

    /**
     * метод возвращает вид со списком всех заданий
     * @param model - модель с данными
     * @param httpSession - объект связанный с работой пользователя
     * @return - вид со всеми заданиями в хранилище
     */
    @GetMapping("/allTask")
    public String allTask(Model model, HttpSession httpSession) {
        model.addAttribute("tasks", taskService.findAll());
        getUser(model, httpSession);
        return "tasks/allTask";
    }

    /**
     * метод возвращает вид со списком заданий со статусом "выполнено"
     * @param model - модель с данными
     * @param httpSession - объект связанный с работой пользователя
     * @return - вид со заданиями
     */
    @GetMapping("/doneTrueTask")
    public String doneTrueTask(Model model, HttpSession httpSession) {
        model.addAttribute("tasks", taskService.findByDone(true));
        getUser(model, httpSession);
        return "tasks/doneTrueTask";
    }

    /**
     * метод возвращает вид со списком заданий со статусом "активно"
     * @param model - модель с данными
     * @param httpSession - объект связанный с работой пользователя
     * @return - вид с заданиями
     */
    @GetMapping("/doneFalseTask")
    public String doneFalseTask(Model model, HttpSession httpSession) {
        model.addAttribute("tasks", taskService.findByDone(false));
        getUser(model, httpSession);
        return "tasks/doneFalseTask";
    }

    /**
     * метод возвращает вид с данными конкретного задания
     * @param id - индификатор задания
     * @param model - модель с данными
     * @param httpSession - объект связанный с работой пользователя
     * @return - вид с заданием
     */
    @GetMapping("/viewTask/{id}")
    public String viewTask(@PathVariable int id, Model model, HttpSession httpSession) {
        try {
            model.addAttribute("task", taskService.findById(id).get());
        } catch (Exception e) {
            httpSession.setAttribute("message", "Ошибка при отображении задания!");
            return "redirect:/tasks/showMessage";
        }
        getUser(model, httpSession);
        return "tasks/viewTask";
    }

    /**
     * метод возвращает вид добавления задания в хранилище
     * @param model - модель данных
     * @param httpSession - объект связанный с работой пользователя
     * @return - вид добавления задания
     */
    @GetMapping("/addTask")
    public String addTask(Model model, HttpSession httpSession) {
        getUser(model, httpSession);
        return "tasks/addTask";
    }

    /**
     * метод добавляет задание в хранилище и возвращает вид со всеми заданиями
     * @param task - задание
     * @return - вид со всеми заданиями
     */
    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task) {
        taskService.add(task);
        return "redirect:/tasks/allTask";
    }

    /**
     * метод возвращает вид редактирования задания
     * @param id - индификатор задания
     * @param model - модель данных
     * @param httpSession - объект связанный с работой пользователя
     * @return - вид редактирования задания
     */
    @GetMapping("/updateTask/{id}")
    public String updateTaskView(@PathVariable int id, Model model, HttpSession httpSession) {
        try {
            model.addAttribute("task", taskService.findById(id).get());
        } catch (Exception e) {
            httpSession.setAttribute("message", "Ошибка при отображении задания!");
            return "redirect:/tasks/showMessage";
        }
        getUser(model, httpSession);
        return "tasks/updateTask";
    }

    /**
     * метод обновляет задание в хранилище и возвращает вид со результатом операции
     * @param task - задание
     * @param httpSession - объект связанный с работой пользователя
     * @return - вид с результатом обновления
     */
    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task, HttpSession httpSession) {
        System.out.println(task.isDone());
        boolean rsl = taskService.update(task);
        if (!rsl) {
            httpSession.setAttribute("message", "Ошибка при обновлении задания!");
            return "redirect:/tasks/showMessage";
        }
        httpSession.setAttribute("message", "Обновление задания выполнено!");
        return "redirect:/tasks/showMessage";
    }

    /**
     * метод удаляет задание по id и возвращает вид с результатом операции
     * @param id - индификатор задания
     * @param httpSession - объект связанный с работой пользователя
     * @return - вид со статусом операции удаления
     */
    @GetMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable int id, HttpSession httpSession) {
        boolean rsl = taskService.delete(id);
        if (!rsl) {
            httpSession.setAttribute("message", "Ошибка при удалении задания!");
            return "redirect:/tasks/showMessage";
        }
        httpSession.setAttribute("message", "Задание удалено!");
        return "redirect:/tasks/showMessage";
    }

    /**
     * метод изменяет статус выполнения задания с id на "выполнено"
     * @param id - индификатор задания
     * @param httpSession - объект связанный с работой пользователя
     * @return - вид
     */
    @GetMapping("/doneTask/{id}")
    public String doneTask(@PathVariable int id, HttpSession httpSession) {
        if (!taskService.doneTrue(id)) {
            httpSession.setAttribute("message", "Ошибка при выполнении задания!");
            return "redirect:/tasks/showMessage";
        }
        return "redirect:/tasks/allTask";
    }

    /**
     * метод возвращает вид с сообщением
     * @param model - модель данных
     * @param httpSession - объект связанный с работой пользователя
     * @return - вид с сообщением
     */
    @GetMapping("/showMessage")
    public String showMessage(Model model, HttpSession httpSession) {
        getUser(model, httpSession);
        model.addAttribute("message", httpSession.getAttribute("message"));
        return "general/showMessage";
    }
}
