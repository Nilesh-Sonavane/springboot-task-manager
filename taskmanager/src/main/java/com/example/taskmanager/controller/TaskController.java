package com.example.taskmanager.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.dto.ApiResponse;
import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createTask(
            @RequestHeader("X-USER-EMAIL") String email,
            @Valid @RequestBody TaskRequest request) {

        taskService.createTask(email, request);
        return ResponseEntity.ok(new ApiResponse("Task created successfully"));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(
            @RequestHeader("X-USER-EMAIL") String email) {

        return ResponseEntity.ok(taskService.getTasks(email));
    }

    @PutMapping("/{id}/done")
    public ResponseEntity<ApiResponse> markTaskAsDone(
            @RequestHeader("X-USER-EMAIL") String email,
            @PathVariable Long id) {

        taskService.markTaskAsDone(email, id);
        return ResponseEntity.ok(new ApiResponse("Task marked as DONE"));
    }
}
