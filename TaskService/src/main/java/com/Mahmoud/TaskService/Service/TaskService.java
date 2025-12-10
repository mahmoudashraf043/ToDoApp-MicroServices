package com.Mahmoud.TaskService.Service;

import com.Mahmoud.TaskService.Dto.TaskDto;
import com.Mahmoud.TaskService.Model.Task;
import com.Mahmoud.TaskService.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepo;

    public Page<Task> getAllUnCompletedTasks(int page) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        task.setUser(user);
        try {
            return taskRepo.save(task);
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("something went wrong please try again later");
        }
    }


    public Task updateTask(int id,TaskDto task) throws Exception {
        Task temp = taskRepo.findById(id).orElseThrow(() -> new Exception(("this task is not exist anymore")));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(temp.getUser().getId() != user.getId()){
            throw new Exception("this task does not belong to this user");
        }
        if(task.getTitle() != null) {
            temp.setTitle(task.getTitle());
        }
        if(task.getDescription() != null) {
            temp.setDescription(task.getDescription());
        }
        if(task.getDueDate() != null) {
            temp.setDueDate(task.getDueDate());
        }
        if(task.isCompleted() != temp.isCompleted()) {
            temp.setCompleted(task.isCompleted());
        }

        return taskRepo.save(temp);

    }

    public String deleteTask(int id) throws Exception {
        Task task = taskRepo.findById(id).orElseThrow(() -> new Exception("this task is not exist anymore"));
        taskRepo.delete(task);
        return "task deleted";
    }
}
