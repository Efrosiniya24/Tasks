package com.example.Tasks.service;

import com.example.Tasks.model.Tasks;
import com.example.Tasks.model.User;
import com.example.Tasks.repository.TasksRepository;
import com.example.Tasks.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TasksService {

    private final TasksRepository tasksRepository;
    private final UserRepository userRepository;

    public TasksService(TasksRepository tasksRepository, UserRepository userRepository) {
        this.tasksRepository = tasksRepository;
        this.userRepository = userRepository;
    }

    public List<Tasks> getAllTasks() {
        return tasksRepository.findAll();
    }

    public Tasks getTasksById(Long id) {
        return tasksRepository.findById(id);
    }

    public Tasks createTasks(Tasks tasks) {
        if (tasks.getUser() != null && tasks.getUser().getId() != null) {
            Long userId = tasks.getUser().getId();
            Optional<User> existingUser = userRepository.findById(userId);
            if (existingUser.isPresent())
                tasks.setUser(existingUser.get());
            else
                throw new RuntimeException("User not found with id: " + userId);
        }
        return tasksRepository.save(tasks);
    }

    public Tasks updateTasks(Long id, Tasks tasksDetails) {
        Tasks tasks = tasksRepository.findById(id);
        if (tasks != null) {
            tasks.setName(tasksDetails.getName());
            tasks.setDescription(tasksDetails.getDescription());
            tasks.setDeadline(tasksDetails.getDeadline());
            if (tasksDetails.getUser() != null && tasksDetails.getUser().getId() != null) {
                Optional<User> user = userRepository.findById(tasksDetails.getUser().getId());
                tasks.setUser(user.orElseThrow(() -> new RuntimeException("User not found")));
            }
            return tasksRepository.save(tasks);
        }
        return null;
    }

    public void deleteTasks(Long id) {
        tasksRepository.deleteById(id);
    }
}
