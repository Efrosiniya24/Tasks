package com.example.Tasks.controller;

import com.example.Tasks.model.Tasks;
import com.example.Tasks.service.TasksService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    private final TasksService tasksService;

    public TasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @GetMapping
    public List<Tasks> getAllTasks() {
        return tasksService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Tasks getTasksById(@PathVariable Long id) {
        return tasksService.getTasksById(id);
    }

    @PostMapping
    public Tasks createTasks(@RequestBody Tasks tasks) {
        return tasksService.createTasks(tasks);
    }

    @PutMapping("/{id}")
    public Tasks updateTasks(@PathVariable Long id, @RequestBody Tasks tasksDetails) {
        return tasksService.updateTasks(id, tasksDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteTasks(@PathVariable Long id) {
        tasksService.deleteTasks(id);
    }
}
