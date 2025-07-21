package com.Mahmoud.AuthService.FeignClient;

import com.Mahmoud.AuthService.DTO.Userprofile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "UserService")
public interface UserClient {
    @PostMapping("/user/")
    ResponseEntity<Void> addUser(@RequestBody Userprofile userprofile);
}
