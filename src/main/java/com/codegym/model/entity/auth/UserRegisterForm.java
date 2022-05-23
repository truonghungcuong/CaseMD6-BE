package com.codegym.model.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterForm {
    @NotEmpty(message = "Không được để trống")
    @Column(unique = true)
    private String email;
    @NotEmpty(message = "Không được để trống")
    @Column(unique = true)
    private String username;
    private String password;
    private String confirmPassword;

    private String fullName;

    private String address;

    public boolean confirmPasswordMatch(){
        return password.equals(confirmPassword);
    }
}
