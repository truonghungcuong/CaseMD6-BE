package com.codegym.service.dish;

import com.codegym.model.entity.Merchant;
import com.codegym.model.entity.dish.category.Category;
import com.codegym.model.entity.dish.Dish;
import com.codegym.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDishService extends IGeneralService<Dish> {
    Page<Dish> findAll(Pageable pageable);

    Page<Dish> findAllByNameContaining(String name, Pageable pageable);

    int countDishByCategoriesIsContaining(Category category);

    Iterable<Dish> findMostPurchased(int top);

    Iterable<Dish> findAllByMerchant(Merchant merchant);

    Iterable<Dish> findAllByMerchant_Id(Long id);

    Iterable<Dish> findDishesWithSameCategoryWith(Long dishId, int limit);
}
