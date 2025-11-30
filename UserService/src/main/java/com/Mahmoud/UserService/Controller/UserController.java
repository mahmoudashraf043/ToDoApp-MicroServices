package com.Mahmoud.UserService.Controller;

import com.Mahmoud.UserService.Dto.UserProfile;
import com.Mahmoud.UserService.Model.User;
import com.Mahmoud.UserService.Service.UserService;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> getUser() throws Exception {

        return new ResponseEntity<>(userService.getUser() , HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> addUser(@RequestBody UserProfile userProfile) throws Exception {
        return new ResponseEntity<>(userService.adduser(userProfile) , HttpStatus.OK);

    }

    @PutMapping("/")
    public ResponseEntity<?> updateUser(@RequestBody UserProfile userProfile) throws Exception {
        return new ResponseEntity<>(userService.updateUser(userProfile) , HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) throws Exception {
        return new ResponseEntity<>(userService.deleteUser(userId) , HttpStatus.OK);
    }
}
