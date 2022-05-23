package com.codegym.service.cart_detail;

import com.codegym.model.entity.Cart;
import com.codegym.model.entity.CartDetail;
import com.codegym.model.entity.dish.Dish;
import com.codegym.service.IGeneralService;

import java.util.Optional;

public interface ICartDetailService extends IGeneralService<CartDetail> {
    Iterable<CartDetail> findAllByCart(Cart cart);

    Optional<CartDetail> findByCartAndDish(Cart cart, Dish dish);

    void deleteAllByCart(Cart cart);
}
