package com.Mahmoud.NotificationService.FeignClient;

import com.Mahmoud.NotificationService.Dto.TaskDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.naming.ConfigurationException;

@FeignClient(name = "TaskService" , configuration = FeignConfig.class)
public interface TaskClient {

    @GetMapping("/due-tomorrow/{userId}")
    public TaskDto dueTomorrow(@PathVariable("userId") Integer userId);
}
