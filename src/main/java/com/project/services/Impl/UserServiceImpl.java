package com.project.services.Impl;

import com.project.dto.UserDto;
import com.project.dto.UserSignUpDto;
import com.project.entity.User;
import com.project.repository.IUserRepository;
import com.project.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    IUserRepository userRepository;

    @Transactional
    @Override
    public UserDto createUser(UserSignUpDto userSignUpDto) {
        validateUser(userSignUpDto);

        if (!(userRepository.findByUserEmail(userSignUpDto.getEmail()).isEmpty() && userRepository.findByUserUsername(userSignUpDto.getUsername()).isEmpty())) {
            logger.error("Duplicate username or email.");
            throw new RuntimeException("Duplicate username or email.");
        }

        User newUser = mapUserFromDto(userSignUpDto);
        return convertUserToDto(userRepository.save(newUser));
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertUserToDto).collect(Collectors.toList());
    }

    private void validateUser(UserSignUpDto userSignUpDto) {
        if (!userSignUpDto.isValid()) {
            logger.error("Invalid User schema can't register new user");
            throw new RuntimeException("Schema violation in user model");
        }
    }

    private User mapUserFromDto(UserSignUpDto userSignUpDto) {
        User newUser = new User();
        newUser.setEmail(userSignUpDto.getEmail());
        newUser.setUsername(userSignUpDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(userSignUpDto.getPassword())); // hashing password
        newUser.setEnabled(true);
        newUser.setRole("USER");
        return newUser;
    }

    private UserDto convertUserToDto(User entity) {
        UserDto dto = new UserDto();
        dto.setEmail(entity.getEmail());
        dto.setUsername(entity.getUsername());
        return dto;
    }
}