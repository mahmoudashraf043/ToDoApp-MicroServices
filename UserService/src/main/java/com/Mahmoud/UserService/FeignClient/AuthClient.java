package com.Mahmoud.UserService.FeignClient;


import com.Mahmoud.UserService.Dto.AuthUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "AuthService")
public interface AuthClient {
    @DeleteMapping("/authUser/{username}")
    ResponseEntity<String> deleteAuthUser(@PathVariable("username") String username);

    @PutMapping("/authUser/")
    ResponseEntity<String> updateAuthUser(@RequestBody AuthUser authUser);
}
