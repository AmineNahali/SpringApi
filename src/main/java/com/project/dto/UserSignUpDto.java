package com.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpDto {
    String username;
    String email;
    String password;

    public boolean isValid(){
        return !(this.username.isBlank() || this.email.isBlank() || this.password.isBlank());
    }
}