package com.Mahmoud.AuthService.Controller;

import com.Mahmoud.AuthService.DTO.ChangePasswordDto;
import com.Mahmoud.AuthService.Model.AuthUser;
import com.Mahmoud.AuthService.Service.AuthUserService;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authUser")
public class AuthUserController {

    @Autowired
    private AuthUserService authUserService;

         @DeleteMapping("/{userId}")
         public ResponseEntity<?> delete(@PathVariable Integer userId) throws Exception {
            return new ResponseEntity<>(authUserService.deleteUser(userId) ,HttpStatus.OK);
        }

        @PutMapping("/changePassword")
        public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto) throws Exception {
             return new ResponseEntity<>(authUserService.changePassword(changePasswordDto) , HttpStatus.OK);
        }

        @PutMapping("/changeUsername")
        public ResponseEntity<?> changeUsername(@RequestBody String username) throws Exception {
            return new ResponseEntity<>(authUserService.changeUsername(username) , HttpStatus.OK);
        }

        @PutMapping("/changeEmail")
        public ResponseEntity<?> changeEmail(@RequestBody String email) throws Exception {
            return new ResponseEntity<>(authUserService.changeEmail(email) , HttpStatus.OK);
        }

        @GetMapping("/{userId}")
    public ResponseEntity<?> getAuthUser(@PathVariable Integer userId) throws Exception {
             return new ResponseEntity<>(authUserService.getUser(userId) , HttpStatus.OK);
        }

        @GetMapping("/getLogedUser/{userId}")
        public ResponseEntity<?> getLoggedUser(@PathVariable Integer userId) throws Exception {
            return new ResponseEntity<>(authUserService.getLoggedUser(userId) , HttpStatus.OK);
        }


}
