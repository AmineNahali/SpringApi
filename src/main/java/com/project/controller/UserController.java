package com.project.controller;

import com.project.dto.UserDto;
import com.project.dto.UserSignUpDto;
import com.project.services.IUserService;
import com.project.services.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    IUserService userService;


    @RequestMapping(value="/register", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestBody UserSignUpDto newUser){
        try {
            UserDto userDto = userService.createUser(newUser);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Log the exception for monitoring purposes
            logger.error("Error processing request", new Exception());
            return new ResponseEntity<>("Error registering user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value="/list", method = RequestMethod.GET)
    public ResponseEntity<Object> list() {
        try {
            List<UserDto> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Log the exception for monitoring purposes
            logger.error("Error processing request", new Exception());
            return new ResponseEntity<>("Error fetching users", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
