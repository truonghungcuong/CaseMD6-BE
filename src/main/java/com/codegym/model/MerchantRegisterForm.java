package com.codegym.model;

import com.codegym.model.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantRegisterForm {
    private User user;
    private String name;
    private String description;
    private String address;
    private String phone;
    private String openTime;
    private String closeTime;

}