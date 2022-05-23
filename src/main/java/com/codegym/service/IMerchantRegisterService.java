package com.codegym.service;

import com.codegym.model.dto.dish.DishDto;
import com.codegym.model.entity.MerchantRegisterRequest;
import com.codegym.model.entity.dish.category.CategoryDTO;
import com.codegym.model.entity.user.User;

import java.util.Optional;

public interface IMerchantRegisterService extends IGeneralService<MerchantRegisterRequest> {
    Optional<MerchantRegisterRequest> findByUserAndReviewed(User user, boolean reviewed);

    Iterable<MerchantRegisterRequest> findMerchantByReviewed(boolean reviewed);

}
