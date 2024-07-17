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

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Override
    public UserDto createUser(UserSignUpDto userSignUpDto){
        User newUser =  new User();
        newUser.setEmail(userSignUpDto.getEmail());
        newUser.setUsername(userSignUpDto.getUsername());
        newUser.setEmail(userSignUpDto.getEmail());
        newUser.setPassword(userSignUpDto.getPassword());
        return convertUserToDto(userRepository.save(newUser));
    }

    @Override
    public List<UserDto> getAllUsers() {
            try {
                return userRepository.findAll().stream().map(this::convertUserToDto).collect(Collectors.toList());
            } catch (DataAccessException e) {
                // Log the exception for monitoring purposes
                //log.error("Failed to fetch users from the database", e);
                throw new RuntimeException("Error fetching users", e);
            } catch (Exception e) {
                // Handle other unexpected exceptions
                //log.error("Unexpected error occurred while fetching users", e);
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