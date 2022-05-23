package com.codegym.model.dto.cart;

import com.codegym.model.entity.dish.Dish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailDto {
    private Dish dish;
    private int quantity;
}
