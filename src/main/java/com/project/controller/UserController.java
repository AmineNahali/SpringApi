package com.project.controller;

import com.project.dto.UserDto;
import com.project.dto.UserSignUpDto;
import com.project.repository.IUserRepository;
import com.project.services.IUserService;
import com.project.services.Impl.UserServiceImpl;
import com.project.utils.SecurityUtils;
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
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    IUserService userService;

    @Autowired
    IUserRepository userRepository;

    @RequestMapping(value = "/api/auth/register", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestBody UserSignUpDto newUser) {
        try {
            UserDto userDto = userService.createUser(newUser);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("Error processing request");
            return new ResponseEntity<>("Error registering user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/api/user/list", method = RequestMethod.GET)
    public ResponseEntity<Object> list() {
        String connected_user = SecurityUtils.getCurrentUserJWT().get();
        try {
            List<UserDto> users = userService.getAllUsers();
            logger.info(connected_user + " has requested list of users");
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("Error processing request");
            return new ResponseEntity<>("Error fetching users", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}