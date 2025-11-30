package com.Mahmoud.UserService.FeignClient;


import com.Mahmoud.UserService.Configuration.FeignConfig;
import com.Mahmoud.UserService.Dto.AuthUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "AuthService" , configuration = FeignConfig.class)
public interface AuthClient {
    @DeleteMapping("/authUser/{userId}")
    ResponseEntity<String> deleteAuthUser(@PathVariable("userId") Integer userId);

}
