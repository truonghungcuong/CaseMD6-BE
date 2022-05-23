package com.codegym.model;

import com.codegym.model.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipperRegisterForm {
    private User user;
    private String name;
    private String phone;
    private String licensePlates;
    private String carName;
}
