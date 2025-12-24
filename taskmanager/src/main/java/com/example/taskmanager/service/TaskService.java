package com.example.taskmanager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.TaskStatus;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
            UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void createTask(String email, TaskRequest request) {
        User user = getUser(email);

        if (taskRepository.existsByTitleAndUser(request.getTitle(), user)) {
            throw new RuntimeException("Task with same title already exists");
        }

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setStatus(TaskStatus.PENDING);
        task.setUser(user);

        taskRepository.save(task);
    }

    public List<TaskResponse> getTasks(String email) {
        User user = getUser(email);

        return taskRepository.findByUser(user)
                .stream()
                .map(t -> new TaskResponse(
                        t.getId(),
                        t.getTitle(),
                        t.getStatus().name()))
                .collect(Collectors.toList());
    }

    public void markTaskAsDone(String email, Long taskId) {
        User user = getUser(email);

        Task task = taskRepository.findByIdAndUser(taskId, user)
                .orElseThrow(() -> new RuntimeException("Task not found or access denied"));

        if (task.getStatus() == TaskStatus.DONE) {
            throw new RuntimeException("Task already marked as DONE");
        }

        task.setStatus(TaskStatus.DONE);
        taskRepository.save(task);
    }
}
