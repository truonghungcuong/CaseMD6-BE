package com.codegym.service.cart;

import com.codegym.model.dto.cart.CartDetailDto;
import com.codegym.model.dto.cart.CartDto;
import com.codegym.model.entity.Cart;
import com.codegym.model.entity.Merchant;
import com.codegym.model.entity.dish.Dish;
import com.codegym.model.entity.user.User;
import com.codegym.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface ICartService extends IGeneralService<Cart> {

    Optional<Cart> findCartByUserAndMerchant(User user, Merchant merchant);

    Iterable<Cart> findAllCartByUser(User user);

    Cart createCartWithUserAndMerchant(User user, Merchant merchant);

    CartDto getCartDtoByUserAndMerchant(User user, Merchant merchant);

    List<CartDto> getAllCartDtoByUser(User user);

    CartDto addDishToCart(User user, CartDetailDto cartDetailDto);

    boolean changeDishQuantityInCart(Cart cart, Dish dish, int amount);

    void emptyCartById(Long cartId);

}
