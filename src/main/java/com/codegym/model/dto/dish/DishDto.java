package com.codegym.model.dto.dish;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishDto {
    private Long id;
    private String dishName;
    private int orderQuantity;
}
