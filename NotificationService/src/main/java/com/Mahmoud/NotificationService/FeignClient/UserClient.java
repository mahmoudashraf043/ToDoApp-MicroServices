package com.Mahmoud.NotificationService.FeignClient;

import com.Mahmoud.NotificationService.Dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "UserService" , configuration = FeignConfig.class)
public interface UserClient {

    @GetMapping("/user/")
    public UserDto getUser();

}
