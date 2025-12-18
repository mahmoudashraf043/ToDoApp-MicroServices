package com.Mahmoud.NotificationService.Dto;

import com.Mahmoud.NotificationService.FeignClient.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "TaskService" , configuration = FeignConfig.class)
public class TaskClient {

    @GetMapping("")
    public String getdueDateTasks() {}
}
