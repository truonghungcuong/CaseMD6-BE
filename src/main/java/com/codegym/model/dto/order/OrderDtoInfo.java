package com.codegym.model.dto.order;

import java.util.Date;

public interface OrderDtoInfo {
    Long getId();

    Date getCreate_Date();

    String getFull_Name();

    String getName();

    double getOrderPrice();

    String getAddress();

    String getPhone();

    int getStatus();


}
