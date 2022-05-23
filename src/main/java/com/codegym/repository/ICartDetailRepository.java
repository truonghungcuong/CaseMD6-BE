package com.codegym.repository;

import com.codegym.model.entity.Cart;
import com.codegym.model.entity.CartDetail;
import com.codegym.model.entity.dish.Dish;
import com.codegym.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public interface ICartDetailRepository extends JpaRepository<CartDetail, Long> {
    Iterable<CartDetail> findAllByCart(Cart cart);

    void deleteAllByCart(Cart cart);

    Optional<CartDetail> findByCartAndDish(Cart cart, Dish dish);

}
