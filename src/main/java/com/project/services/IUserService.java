package com.project.services;

import com.project.dto.UserDto;
import com.project.dto.UserSignUpDto;
import com.project.entity.User;

import java.util.List;

public interface IUserService {
    public UserDto createUser(UserSignUpDto userSignUpDto);
    public List<UserDto> getAllUsers();
}
