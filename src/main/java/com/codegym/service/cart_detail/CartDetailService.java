package com.codegym.service.cart_detail;

import com.codegym.model.entity.Cart;
import com.codegym.model.entity.CartDetail;
import com.codegym.model.entity.dish.Dish;
import com.codegym.repository.ICartDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartDetailService implements ICartDetailService {
    @Autowired
    ICartDetailRepository cartDetailRepository;

    @Override
    public Iterable<CartDetail> findAll() {
        return cartDetailRepository.findAll();
    }

    @Override
    public Optional<CartDetail> findById(Long id) {
        return cartDetailRepository.findById(id);
    }

    @Override
    public CartDetail save(CartDetail cartDetail) {
        return cartDetailRepository.save(cartDetail);
    }

    @Override
    public void deleteById(Long id) {
        cartDetailRepository.deleteById(id);
    }

    @Override
    public Iterable<CartDetail> findAllByCart(Cart cart) {
        return cartDetailRepository.findAllByCart(cart);
    }

    @Override
    public Optional<CartDetail> findByCartAndDish(Cart cart, Dish dish) {
        return cartDetailRepository.findByCartAndDish(cart, dish);
    }

    @Override
    public void deleteAllByCart(Cart cart) {
        cartDetailRepository.deleteAllByCart(cart);
    }

}
