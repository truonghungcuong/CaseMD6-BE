package com.codegym.model.dto.order;


import com.codegym.model.entity.user.User;
import org.springframework.stereotype.Component;

import com.codegym.model.entity.dish.Dish;
import com.codegym.model.entity.user.User;

import java.util.Date;


import java.time.LocalDate;
import java.util.Date;
@Component
public interface OrderByQueryDto {
    Long getId ();
    LocalDate getCreate_Date();
    double getDiscount_Amount();
    String getRestaurant_Note();
    double getService_Fee();
    double getShipping_Fee();
    String getShipping_Note();
    double getTotal_Fee();
    Long getUser_Id();
    String getFull_Name ();
    String getPhone ();
    String getAddress ();
    String getEmail ();
}
