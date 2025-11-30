package com.Mahmoud.AuthService.Service;

import com.Mahmoud.AuthService.DTO.ChangePasswordDto;
import com.Mahmoud.AuthService.DTO.Userprofile;
import com.Mahmoud.AuthService.FeignClient.UserClient;
import com.Mahmoud.AuthService.Model.AuthUser;
import com.Mahmoud.AuthService.Repository.AuthRepository;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthUserService {

    @Autowired
    AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserClient userClient;

    public String deleteUser(Integer userId) throws Exception {
        try {
            String temp = SecurityContextHolder.getContext().getAuthentication().getName();
            AuthUser loggedInUser = authRepository.findByUsername(temp);
            AuthUser user = authRepository.findById(userId).orElseThrow(() -> new Exception("User Not Found"));
            if (loggedInUser.getId().equals(user.getId())) {
                Integer deletedUser = authRepository.deleteByUsername(temp);
                if (deletedUser == 0) {
                    return "The user does not exist";
                }
                return "delete has been done";
            } else {
                return "You are not allowed to delete this user";
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong please try again");
        }
    }


    public AuthUser getUser(Integer userId) throws Exception {
        try{
            String temp = SecurityContextHolder.getContext().getAuthentication().getName();
            AuthUser loggedInUser = authRepository.findByUsername(temp);
            if(loggedInUser.getId().equals(userId)) {
                return authRepository.findById(userId).orElseThrow(() -> new Exception("User Not Found"));
            }else{
                throw new Exception("This user is not logged in");
            }

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Something went wrong please try again");
        }
    }

    public AuthUser getLoggedUser(Integer userId) throws Exception {
        try{
            String temp = SecurityContextHolder.getContext().getAuthentication().getName();
            AuthUser loggedInUser = authRepository.findByUsername(temp);
            AuthUser dbUser = authRepository.findById(userId).orElseThrow(() -> new Exception("User Not Found"));
            if(loggedInUser.getId().equals(dbUser.getId())) {
                return dbUser;
            }else {
                throw new Exception("This user is not logged in");
            }

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Something went wrong please try again");
        }
    }

    public String changePassword(ChangePasswordDto changePasswordDto) throws Exception {
        try {
            String temp = SecurityContextHolder.getContext().getAuthentication().getName();
            AuthUser loggedInUser = authRepository.findByUsername(temp);

            if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), loggedInUser.getPassword())) {
                return "Old password is incorrect";
            }
            if (!passwordEncoder.matches(changePasswordDto.getNewPassword(), loggedInUser.getPassword())) {
                return "please choose password different from the old password";
            }

            loggedInUser.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            authRepository.save(loggedInUser);
            return "password changed";
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("something went wrong please try again");
        }
    }

    public String changeUsername(String username) throws Exception {
        try {
            String temp = SecurityContextHolder.getContext().getAuthentication().getName();
            AuthUser loggedInUser = authRepository.findByUsername(temp);

            if (authRepository.existsByUsername(username)) {
                return "Username is already taken";
            }
            loggedInUser.setUsername(username);
            Userprofile userprofile = new Userprofile();
            userprofile.setUsername(username);
            ResponseEntity<String> response = userClient.updateUserProfile(userprofile);
            if(response.getStatusCode() .is2xxSuccessful()){
                authRepository.save(loggedInUser);
                return "email changed";
            }else {
                throw new Exception("Something went wrong with user service");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Something went wrong please try again");
        }
    }

    public String changeEmail(String email) throws Exception {
        try{
            String temp = SecurityContextHolder.getContext().getAuthentication().getName();
            AuthUser loggedInUser = authRepository.findByUsername(temp);
            if (authRepository.existsByEmail(email)) {
                return "Email is already taken";
            }
            loggedInUser.setEmail(email);
            Userprofile userprofile = new Userprofile();
            userprofile.setEmail(email);
           ResponseEntity<String> response = userClient.updateUserProfile(userprofile);
           if(response.getStatusCode() .is2xxSuccessful()){
               authRepository.save(loggedInUser);
               return "email changed";
           }else {
                throw new Exception("Something went wrong with user service");
           }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong please try again");
        }
    }
}
