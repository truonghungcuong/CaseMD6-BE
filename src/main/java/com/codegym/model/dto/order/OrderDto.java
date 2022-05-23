package com.codegym.model.dto.order;

import com.codegym.model.dto.cart.CartDto;
import com.codegym.model.entity.DeliveryInfo;
import com.codegym.model.entity.Merchant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private CartDto cart;
    private DeliveryInfo deliveryInfo;
    private Merchant merchant;
    private Date createDate;
    private int status;
}
