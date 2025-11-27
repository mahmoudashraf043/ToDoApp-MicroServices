package com.Mahmoud.UserService.Service;

import com.Mahmoud.UserService.Dto.UserProfile;
import com.Mahmoud.UserService.FeignClient.AuthClient;
import com.Mahmoud.UserService.Model.User;
import com.Mahmoud.UserService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        user.setUsername(userProfile.getUsername());
        user.setEmail(userProfile.getEmail());
        user.setFirstName(userProfile.getFirstName());
        user.setLastName(userProfile.getLastName());

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
            User temp = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            ;
            temp.setFirstName(userProfile.getFirstName());
            temp.setLastName(userProfile.getLastName());
            temp.setEmail(userProfile.getEmail());
            userRepository.save(temp);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("something went wrong");
        }
        return "update has been done";
    }
    public String deleteUser(String username) throws Exception {
        try {
            String temp = SecurityContextHolder.getContext().getAuthentication().getName();
            if (temp.equals(username)) {
                userRepository.delete(userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found")));
                authClient.deleteAuthUser(username);
                return "delete has been done";
            }
            throw new Exception("This user can not be deleted by You");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong please try again");
        }
    }
}
