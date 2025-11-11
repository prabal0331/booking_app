package com.cnh.booking.service;

import com.cnh.booking.dto.UserLogin;
import com.cnh.booking.dto.UserRegister;
import com.cnh.booking.model.User;
import com.cnh.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String registerUser(UserRegister register){
        Optional<User> findUser = userRepository.findByEmail(register.getEmail());
        if(findUser.isPresent()){
            return "User already exist";
        }
        if(!register.getPassword().equals(register.getConfirmPassword())){
            return "Password mismatch";
        }
        User newUser = new User();
        newUser.setName(register.getName());
        newUser.setEmail(register.getEmail());
        newUser.setGender(register.getGender());
        newUser.setPassword(register.getPassword());
        userRepository.save(newUser);
        return "Registration Successful";
    }

    public String loginUser(UserLogin login){
        Optional<User> findUser = userRepository.findByEmail(login.getEmail());
        if(findUser.isEmpty()){
            return "Invalid User";
        }
        if(!findUser.get().getPassword().equals(login.getPassword())){
            return "Invalid Password";
        }
        return "Login Successful";
    }
}
