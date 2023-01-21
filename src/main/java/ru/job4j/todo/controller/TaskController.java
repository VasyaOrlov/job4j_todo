package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskServiceInterface;

@Controller
@ThreadSafe
@AllArgsConstructor
public class TaskController {

    private TaskServiceInterface taskService;

    @GetMapping("/allTask")
    public String allTask(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "allTask";
    }

    @GetMapping("/doneTrueTask")
    public String doneTrueTask(Model model) {
        model.addAttribute("tasks", taskService.findDoneTrue());
        return "doneTrueTask";
    }

    @GetMapping("/doneFalseTask")
    public String doneFalseTask(Model model) {
        model.addAttribute("tasks", taskService.findDoneFalse());
        return "doneFalseTask";
    }

    @GetMapping("/viewTask/{id}")
    public String viewTask(@PathVariable int id, Model model) {
        model.addAttribute("task", taskService.findById(id).get());
        return "viewTask";
    }

    @GetMapping("/addTask")
    public String addTask() {
        return "addTask";
    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task) {
        taskService.add(task);
        return "redirect:/allTask";
    }

    @GetMapping("/updateTask/{id}")
    public String updateTaskView(@PathVariable int id, Model model) {
        model.addAttribute("task", taskService.findById(id).get());
        return "updateTask";
    }

    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task) {
        System.out.println(task.isDone());
        boolean rsl = taskService.update(task);
        if (!rsl) {
            return "redirect:/updateTaskFail";
        }
        return "redirect:/updateTaskSuccess";
    }

    @GetMapping("/updateTaskFail")
    public String updateTaskFail() {
        return "updateTaskFail";
    }

    @GetMapping("/updateTaskSuccess")
    public String updateTaskSuccess() {
        return "/updateTaskSuccess";
    }

    @GetMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable int id) {
        boolean rsl = taskService.delete(id);
        if (!rsl) {
            return "redirect:/deleteTaskFail";
        }
        return "redirect:/deleteTaskSuccess";
    }

    @GetMapping("/deleteTaskFail")
    public String deleteTaskFail() {
        return "deleteTaskFail";
    }

    @GetMapping("/deleteTaskSuccess")
    public String deleteTaskSuccess() {
        return "deleteTaskSuccess";
    }

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

    @GetMapping("/doneTaskFail")
    public String doneTaskFail() {
        return "doneTaskFail";
    }
}
