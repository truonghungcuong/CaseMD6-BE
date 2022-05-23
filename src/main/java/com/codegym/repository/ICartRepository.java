package com.codegym.repository;

import com.codegym.model.entity.Cart;
import com.codegym.model.entity.Merchant;
import com.codegym.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findCartByUserAndMerchant(User user, Merchant merchant);

    Iterable<Cart> findCartByUser(User user);


}
