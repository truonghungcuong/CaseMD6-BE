package com.codegym.service.merchant;

import com.codegym.model.dto.customer.ICustomerDto;
import com.codegym.model.dto.dish.DishDto;
import com.codegym.model.dto.order.OrderByQueryDto;
import com.codegym.model.entity.Merchant;
import com.codegym.service.IGeneralService;

import java.time.LocalDate;
import java.util.Optional;

public interface IMerchantService extends IGeneralService<Merchant>{
    Optional<Merchant> findMerchantByUserId(Long id);

    Optional<Merchant> findMerchantByUser_Id(Long userId);

    Iterable<DishDto> getAllDishDTO(Long id);

    Iterable<ICustomerDto> getAllCustomerDto (Long id);

    Iterable<OrderByQueryDto> finAllMerchantOrderByCustomerId (Long merchantId, Long userId);

    Iterable<OrderByQueryDto> finAllOrderByMerchantIdInPeriod (Long id, LocalDate startTime, LocalDate endTime);
}