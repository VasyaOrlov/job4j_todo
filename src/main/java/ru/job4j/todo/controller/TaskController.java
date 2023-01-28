package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryServiceInterface;
import ru.job4j.todo.service.PriorityServiceInterface;
import ru.job4j.todo.service.TaskServiceInterface;
import javax.servlet.http.HttpSession;

import java.util.List;

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
    private PriorityServiceInterface priorityService;
    private CategoryServiceInterface categoryService;

    /**
     * метод возвращает вид со списком всех заданий
     * @param model - модель с данными
     * @param httpSession - объект связанный с работой пользователя
     * @return - вид со всеми заданиями в хранилище
     */
    @GetMapping("/all")
    public String all(Model model, HttpSession httpSession) {
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("header", "Все задания");
        getUser(model, httpSession);
        return "tasks/all";
    }

    /**
     * метод возвращает вид со списком заданий со статусом "выполнено"
     * @param model - модель с данными
     * @param httpSession - объект связанный с работой пользователя
     * @return - вид со заданиями
     */
    @GetMapping("/doneTrue")
    public String doneTrue(Model model, HttpSession httpSession) {
        model.addAttribute("tasks", taskService.findByDone(true));
        model.addAttribute("header", "Выполненные задания");
        getUser(model, httpSession);
        return "tasks/all";
    }

    /**
     * метод возвращает вид со списком заданий со статусом "активно"
     * @param model - модель с данными
     * @param httpSession - объект связанный с работой пользователя
     * @return - вид с заданиями
     */
    @GetMapping("/doneFalse")
    public String doneFalse(Model model, HttpSession httpSession) {
        model.addAttribute("tasks", taskService.findByDone(false));
        model.addAttribute("header", "Актуальные задания");
        getUser(model, httpSession);
        return "tasks/all";
    }

    /**
     * метод возвращает вид с данными конкретного задания
     * @param id - индификатор задания
     * @param model - модель с данными
     * @param httpSession - объект связанный с работой пользователя
     * @return - вид с заданием
     */
    @GetMapping("/view/{id}")
    public String view(@PathVariable int id, Model model, HttpSession httpSession) {
        try {
            model.addAttribute("task", taskService.findById(id).get());
        } catch (Exception e) {
            httpSession.setAttribute("message", "Ошибка при отображении задания!");
            return "redirect:/tasks/showMessage";
        }
        getUser(model, httpSession);
        return "tasks/view";
    }

    /**
     * метод возвращает вид добавления задания в хранилище
     * @param model - модель данных
     * @param httpSession - объект связанный с работой пользователя
     * @return - вид добавления задания
     */
    @GetMapping("/add")
    public String add(Model model, HttpSession httpSession) {
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        getUser(model, httpSession);
        return "tasks/add";
    }

    /**
     * метод добавляет задание в хранилище и возвращает вид со всеми заданиями
     * @param task - задание
     * @return - вид со всеми заданиями
     */
    @PostMapping("/create")
    public String create(@ModelAttribute Task task,
                         @RequestParam("category.id") List<Integer> listId,
                         HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        task.setUser(user);
        List<Category> categories = categoryService.findByID(listId);
        if (categories.isEmpty()) {
            httpSession.setAttribute("message", "Ошибка при добавлении задания!");
            return "redirect:/tasks/showMessage";
        }
        task.setCategories(categories);
        if (taskService.add(task).isEmpty()) {
            httpSession.setAttribute("message", "Ошибка при добавлении задания!");
            return "redirect:/tasks/showMessage";
        }
        return "redirect:/tasks/all";
    }

    /**
     * метод возвращает вид редактирования задания
     * @param id - индификатор задания
     * @param model - модель данных
     * @param httpSession - объект связанный с работой пользователя
     * @return - вид редактирования задания
     */
    @GetMapping("/update/{id}")
    public String updateView(@PathVariable int id, Model model, HttpSession httpSession) {
        try {
            model.addAttribute("task", taskService.findById(id).get());
        } catch (Exception e) {
            httpSession.setAttribute("message", "Ошибка при отображении задания!");
            return "redirect:/tasks/showMessage";
        }
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        getUser(model, httpSession);
        return "tasks/update";
    }

    /**
     * метод обновляет задание в хранилище и возвращает вид со результатом операции
     * @param task - задание
     * @param httpSession - объект связанный с работой пользователя
     * @return - вид с результатом обновления
     */
    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task task,
                             @RequestParam("category.id") List<Integer> listId,
                             HttpSession httpSession) {
        List<Category> categories = categoryService.findByID(listId);
        if (categories.isEmpty()) {
            httpSession.setAttribute("message", "Ошибка при обновлении задания!");
            return "redirect:/tasks/showMessage";
        }
        task.setCategories(categories);
        User user = (User) httpSession.getAttribute("user");
        task.setUser(user);
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
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, HttpSession httpSession) {
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
    @GetMapping("/done/{id}")
    public String done(@PathVariable int id, HttpSession httpSession) {
        if (!taskService.doneTrue(id)) {
            httpSession.setAttribute("message", "Ошибка при выполнении задания!");
            return "redirect:/tasks/showMessage";
        }
        return "redirect:/tasks/all";
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
