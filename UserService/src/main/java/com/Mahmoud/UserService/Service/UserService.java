package com.Mahmoud.UserService.Service;

import com.Mahmoud.UserService.Dto.UserProfile;
import com.Mahmoud.UserService.FeignClient.AuthClient;
import com.Mahmoud.UserService.Model.User;
import com.Mahmoud.UserService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthClient authClient;

    public User getUser() throws Exception {

        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return user;
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("something went wrong");
        }

    }
    public String adduser(UserProfile userProfile) throws Exception {
        User user = new User();
        user.setId(userProfile.getUserId());
        user.setUsername(userProfile.getUsername());
        user.setEmail(userProfile.getEmail());
        user.setFirstName(userProfile.getFirstName());
        user.setLastName(userProfile.getLastName());
        user.setPhone(userProfile.getPhone());
        try {
            userRepository.save(user);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("something went wrong");
        }
        return "User added successfully";

    }
    public String updateUser(UserProfile userProfile) throws Exception {

        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User temp = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));;
            if(temp.getId() != userProfile.getUserId()) {
                throw new Exception("this is not the logged in user");
            }
            temp.setUsername(userProfile.getUsername() != null? userProfile.getUsername() : temp.getUsername());
            temp.setFirstName(userProfile.getFirstName() !=null?userProfile.getFirstName():temp.getFirstName());
            temp.setLastName(userProfile.getLastName() != null?userProfile.getLastName():temp.getLastName());
            temp.setEmail(userProfile.getEmail() != null?userProfile.getEmail():temp.getEmail());
            temp.setPhone(userProfile.getPhone() != null?userProfile.getPhone():temp.getPhone());
            userRepository.save(temp);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("something went wrong");
        }
        return "update has been done";
    }
    public String deleteUser(Integer userId) throws Exception {
        try {
            String temp = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(temp).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            if (user.getId() == userId) {
               ResponseEntity<String> response =  authClient.deleteAuthUser(userId);
               if(response.getStatusCode().is2xxSuccessful()){
                   userRepository.delete(user);
                   return "delete has been done";
               }else {
                   throw new Exception("something went wrong with the auth service");
               }
            }
            throw new Exception("This user can not be deleted by You");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong please try again");
        }
    }
}
