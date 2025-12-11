package com.Mahmoud.TaskService.Service;

import com.Mahmoud.TaskService.Dto.TaskDto;
import com.Mahmoud.TaskService.Dto.UserDto;
import com.Mahmoud.TaskService.FeignClient.UserClient;
import com.Mahmoud.TaskService.Model.Task;
import com.Mahmoud.TaskService.Repository.TaskRepository;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepo;
    @Autowired
    private UserClient userClient;

    public Page<Task> getAllUnCompletedTasks(int page) {

        UserDto user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageRequest.of(page, 10 , Sort.by("dueDate").ascending());
        return taskRepo.getAllUnCompletedTasksOfUser(user.getId() , pageable);
    }

    public Page<Task> getAllCompletedTasks(int page) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageRequest.of(page, 10 , Sort.by("dueDate").ascending());
        return taskRepo.getAllCompletedTasksOfUser(user.getId() , pageable);
    }

    public Task getTaskById(int taskId) throws Exception {

        return taskRepo.findByIdAndCompletedIsFalse(taskId)
                .orElseThrow(() -> new Exception("This task does not exist anymore"));
    }

    public Task createTask(TaskDto taskDto) throws Exception {
        Task task = new Task();
        task.setCompleted(false);
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDueDate(taskDto.getDueDate());
        task.setUserId(taskDto.getUserId());
        try {
            return taskRepo.save(task);
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("something went wrong please try again later");
        }
    }


    public Task updateTask(int id,TaskDto task) throws Exception {
        try {
            Task temp = taskRepo.findById(id).orElseThrow(() -> new Exception(("this task is not exist anymore")));
            UserDto userDto = userClient.getUser();

            if (temp.getUserId() != userDto.getUserId()) {
                throw new Exception("this task does not belong to this user");
            }

            if (task.getTitle() != null) {
                temp.setTitle(task.getTitle());
            }
            if (task.getDescription() != null) {
                temp.setDescription(task.getDescription());
            }
            if (task.getDueDate() != null) {
                temp.setDueDate(task.getDueDate());
            }
            return taskRepo.save(temp);
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("something went wrong please try again later");
        }

    }

    public String deleteTask(int id) throws Exception {
        try {
            Task task = taskRepo.findById(id).orElseThrow(() -> new Exception("this task is not exist anymore"));
            UserDto userDto = userClient.getUser();
            if (task.getUserId() != userDto.getUserId()) {
                throw new Exception("this task does not belong to this user");
            }
            taskRepo.delete(task);
            return "task deleted";
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("something went wrong please try again later");
        }
    }
}
