package com.cnh.booking.controller;

import com.cnh.booking.dto.UserLogin;
import com.cnh.booking.dto.UserRegister;
import com.cnh.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//To register or login for a user
@RestController
@RequestMapping("auth")
public class UserController {

    @Autowired
    private UserService userService;

    //Registering an user
    @PostMapping("register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegister userRegister){
        String message = userService.registerUser(userRegister);
        if(message.equals("Registration Successful")){
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    //login for a user
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLogin userLogin){
        String message = userService.loginUser(userLogin);
        if(message.equals("Login Successful")){
            return ResponseEntity.status(200).body(message);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
