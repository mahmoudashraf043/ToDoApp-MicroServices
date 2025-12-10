package com.Mahmoud.TaskService.Controller;

import com.Mahmoud.TaskService.Dto.TaskDto;
import com.Mahmoud.TaskService.Model.Task;
import com.Mahmoud.TaskService.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/uncompleted/")
    public ResponseEntity<Page<Task>> getAllUnCompletedTasks(@RequestParam(defaultValue = "0") int page) {
        return new ResponseEntity<>(taskService.getAllUnCompletedTasks(page) , HttpStatus.OK);
    }
    @GetMapping("/completed/")
    public ResponseEntity<Page<Task>> getAllCompletedTasks(@RequestParam(defaultValue = "0") int page) {
        return new ResponseEntity<>(taskService.getAllCompletedTasks(page) , HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable("taskId") Integer taskId) throws Exception {
        return new ResponseEntity<>(taskService.getTaskById(taskId) , HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Task> createTask(@RequestBody TaskDto taskDto) throws Exception {
        return new ResponseEntity<>(taskService.createTask(taskDto) , HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer taskId,@RequestBody TaskDto taskDto) throws Exception {
        return new ResponseEntity<>(taskService.updateTask(taskId ,taskDto) , HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id) throws Exception {
        return new ResponseEntity<>(taskService.deleteTask(id) , HttpStatus.OK);
    }


}
