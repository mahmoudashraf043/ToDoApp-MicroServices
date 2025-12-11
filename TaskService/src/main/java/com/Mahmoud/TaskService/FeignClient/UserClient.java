package com.Mahmoud.TaskService.FeignClient;

import com.Mahmoud.TaskService.Configuration.FeignClientConfig;
import com.Mahmoud.TaskService.Dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "UserService" , configuration = FeignClientConfig.class)
public interface UserClient {
    @GetMapping("/user/")
    public UserDto getUser();

}
