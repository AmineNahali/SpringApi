package com.project.services.Impl;

import com.project.dto.UserDto;
import com.project.dto.UserSignUpDto;
import com.project.entity.User;
import com.project.repository.IUserRepository;
import com.project.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    IUserRepository userRepository;

    @Override
    public UserDto createUser(UserSignUpDto userSignUpDto) {
        User newUser = new User();
        if (userSignUpDto.isValid()) {
            newUser.setEmail(userSignUpDto.getEmail());
            newUser.setUsername(userSignUpDto.getUsername());
            newUser.setPassword(userSignUpDto.getPassword());
            try {
                return convertUserToDto(userRepository.save(newUser));
            } catch (DataAccessException e) {
                logger.error("Failed to save user", e);
                throw new RuntimeException("Error saving user", e);
            } catch (Exception e) {
                logger.error("Unexpected error occurred while fetching users", e);
                throw new RuntimeException("An unexpected error occurred", e);
            }
        }else {
            logger.error("Unexpected error occurred while fetching users", new Exception());
            throw new RuntimeException("An unexpected error occurred", new Exception());
        }
    }

    @Override
    public List<UserDto> getAllUsers() {
        try {
            return userRepository.findAll().stream().map(this::convertUserToDto).collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("Failed to fetch users from the database", e);
            throw new RuntimeException("Error fetching users", e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while fetching users", e);
            throw new RuntimeException("An unexpected error occurred", e);
        }
    }

    private UserDto convertUserToDto(User entity) {
        UserDto dto = new UserDto();
        dto.setEmail(entity.getEmail());
        dto.setUsername(entity.getUsername());
        return dto;
    }
}