package com.project.controller;

import com.project.dto.UserDto;
import com.project.dto.UserSignUpDto;
import com.project.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;


    @RequestMapping(value="/register", method = RequestMethod.POST)
    public UserDto register(@RequestBody UserSignUpDto newUser){
        return userService.createUser(newUser);
    }


    @RequestMapping(value="/list", method = RequestMethod.GET)
    public ResponseEntity<Object> list() {
        try {
            List<UserDto> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Log the exception for monitoring purposes
            System.out.println("Error processing request");
            return new ResponseEntity<>("Error fetching users", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
