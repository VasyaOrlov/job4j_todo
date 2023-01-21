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
public class TaskController {

    private TaskServiceInterface taskService;

    /**
     * метод возвращает вид со списком всех заданий
     * @param model - модель с данными
     * @return - вид со всеми заданиями в хранилище
     */
    @GetMapping("/allTask")
    public String allTask(Model model, HttpSession httpSession) {
        model.addAttribute("tasks", taskService.findAll());
        getUser(model, httpSession);
        return "allTask";
    }

    /**
     * метод возвращает вид со списком заданий со статусом "выполнено"
     * @param model - модель с данными
     * @return - вид со заданиями
     */
    @GetMapping("/doneTrueTask")
    public String doneTrueTask(Model model, HttpSession httpSession) {
        model.addAttribute("tasks", taskService.findDoneTrue());
        getUser(model, httpSession);
        return "doneTrueTask";
    }

    /**
     * метод возвращает вид со списком заданий со статусом "активно"
     * @param model - модель с данными
     * @return - вид с заданиями
     */
    @GetMapping("/doneFalseTask")
    public String doneFalseTask(Model model, HttpSession httpSession) {
        model.addAttribute("tasks", taskService.findDoneFalse());
        getUser(model, httpSession);
        return "doneFalseTask";
    }

    /**
     * метод возвращает вид с данными конкретного задания
     * @param id - индификатор задания
     * @param model - модель с данными
     * @return - вид с заданием
     */
    @GetMapping("/viewTask/{id}")
    public String viewTask(@PathVariable int id, Model model, HttpSession httpSession) {
        model.addAttribute("task", taskService.findById(id).get());
        getUser(model, httpSession);
        return "viewTask";
    }

    /**
     * метод возвращает вид добавления задания в хранилище
     * @return - вид добавления задания
     */
    @GetMapping("/addTask")
    public String addTask(Model model, HttpSession httpSession) {
        getUser(model, httpSession);
        return "addTask";
    }

    /**
     * метод добавляет задание в хранилище и возвращает вид со всеми заданиями
     * @param task - задание
     * @return - вид со всеми заданиями
     */
    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task) {
        taskService.add(task);
        return "redirect:/allTask";
    }

    /**
     * метод возвращает вид редактирования задания
     * @param id - индификатор задания
     * @param model - модель данных
     * @return - вид редактирования задания
     */
    @GetMapping("/updateTask/{id}")
    public String updateTaskView(@PathVariable int id, Model model, HttpSession httpSession) {
        model.addAttribute("task", taskService.findById(id).get());
        getUser(model, httpSession);
        return "updateTask";
    }

    /**
     * метод обновляет задание в хранилище и возвращает вид со результатом операции
     * @param task - задание
     * @return - вид с результатом обновления
     */
    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task) {
        System.out.println(task.isDone());
        boolean rsl = taskService.update(task);
        if (!rsl) {
            return "redirect:/updateTaskFail";
        }
        return "redirect:/updateTaskSuccess";
    }

    /**
     * метод возвращает вид неудачного обновления задания
     * @return - вид ошибки обновления
     */
    @GetMapping("/updateTaskFail")
    public String updateTaskFail(Model model, HttpSession httpSession) {
        getUser(model, httpSession);
        return "updateTaskFail";
    }

    /**
     * метод возвращает вид успешного обновления задания
     * @return - вид успешного обновления задания
     */
    @GetMapping("/updateTaskSuccess")
    public String updateTaskSuccess(Model model, HttpSession httpSession) {
        getUser(model, httpSession);
        return "/updateTaskSuccess";
    }

    /**
     * метод удаляет задание по id и возвращает вид с результатом операции
     * @param id - индификатор задания
     * @return - вид со статусом операции удаления
     */
    @GetMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable int id) {
        boolean rsl = taskService.delete(id);
        if (!rsl) {
            return "redirect:/deleteTaskFail";
        }
        return "redirect:/deleteTaskSuccess";
    }

    /**
     * возвращает вид ошибки при удалении задания
     * @return - вид ошибки прри удалении
     */
    @GetMapping("/deleteTaskFail")
    public String deleteTaskFail(Model model, HttpSession httpSession) {
        getUser(model, httpSession);
        return "deleteTaskFail";
    }

    /**
     * метод возвращает вид успешного удаления задания
     * @return - вид успешного удаления
     */
    @GetMapping("/deleteTaskSuccess")
    public String deleteTaskSuccess(Model model, HttpSession httpSession) {
        getUser(model, httpSession);
        return "deleteTaskSuccess";
    }

    /**
     * метод изменяет статус выполнения задания с id на "выполнено"
     * @param id - индификатор задания
     * @return - вид
     */
    @GetMapping("/doneTask/{id}")
    public String doneTask(@PathVariable int id) {
        Task task = taskService.findById(id).get();
        task.setDone(true);
        boolean rsl = taskService.update(task);
        if (!rsl) {
            return "redirect:/doneTaskFail";
        }
        return "redirect:/allTask";
    }

    /**
     * возвращает вид с ошибкой при смене статуса задания на "выполнено"
     * @return - вид с ошибкой
     */
    @GetMapping("/doneTaskFail")
    public String doneTaskFail(Model model, HttpSession httpSession) {
        getUser(model, httpSession);
        return "doneTaskFail";
    }
}
