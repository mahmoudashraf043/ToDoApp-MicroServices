package com.Mahmoud.AuthService.FeignClient;

import com.Mahmoud.AuthService.Configuration.FeignConfig;
import com.Mahmoud.AuthService.DTO.Userprofile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "UserService" , configuration = FeignConfig.class)
public interface UserClient {

    @PostMapping("/user/")
    ResponseEntity<String> addUserProfile(@RequestBody Userprofile userprofile);
    @PutMapping("/user/")
    ResponseEntity<String> updateUserProfile(@RequestBody Userprofile userprofile);
}
