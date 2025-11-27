package com.Mahmoud.AuthService.Service;

import com.Mahmoud.AuthService.DTO.LoginRequest;
import com.Mahmoud.AuthService.DTO.LoginResponse;
import com.Mahmoud.AuthService.DTO.RegisterRequest;
import com.Mahmoud.AuthService.DTO.Userprofile;
import com.Mahmoud.AuthService.FeignClient.UserClient;
import com.Mahmoud.AuthService.Jwt.JwtUtils;
import com.Mahmoud.AuthService.Model.AuthUser;
import com.Mahmoud.AuthService.Repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserClient userClient;

    public String register(RegisterRequest registerRequest) {
        if(authRepository.findByUsername(registerRequest.getUsername()) != null) {
            throw new RuntimeException("Username is already in use");
        }


        AuthUser authUser = new AuthUser();
        try {
        authUser.setUsername(registerRequest.getUsername());
        authUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        authUser.setEmail(registerRequest.getEmail());
        authRepository.save(authUser);


        Userprofile userprofile = new Userprofile();
        userprofile.setUserId(authUser.getId());
        userprofile.setFirstName(registerRequest.getFirstName());
        userprofile.setLastName(registerRequest.getLastName());
        userprofile.setPhone(registerRequest.getPhone());


            userClient.addUserProfile(userprofile);

        }catch (Exception e) {
            e.printStackTrace();
            authRepository.deleteByUsername((authUser.getUsername()));
            throw new RuntimeException("Something went wrong please try again");
        }
        return "User has been created successfully";
    }

    public LoginResponse login(LoginRequest loginRequest) {
        AuthUser authUser = authRepository.findByUsername(loginRequest.getUsername());
        if(authUser == null) {
            throw new RuntimeException("User Not Found");
        }
        if(!passwordEncoder.matches(loginRequest.getPassword(), authUser.getPassword())) {
            throw new RuntimeException("Wrong Password");
        }
        return jwtUtils.generateToken(authUser.getUsername() , authUser.getEmail());

    }
}
