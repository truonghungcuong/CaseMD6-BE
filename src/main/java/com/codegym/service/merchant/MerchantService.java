package com.codegym.service.merchant;

import com.codegym.model.dto.customer.ICustomerDto;
import com.codegym.model.dto.dish.DishDto;
import com.codegym.model.dto.order.OrderByQueryDto;
import com.codegym.model.entity.Merchant;
import com.codegym.model.entity.Order;
import com.codegym.model.entity.dish.Dish;
import com.codegym.repository.IMerchantRepository;
import com.codegym.repository.IOrderRepository;
import com.codegym.service.dish.IDishService;
import com.codegym.service.order_detail.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MerchantService implements IMerchantService {
    @Autowired
    private IMerchantRepository merchantRepository;

    @Autowired
    private IDishService dishService;

    @Autowired
    IOrderDetailService orderDetailService;

    @Autowired
    IOrderRepository orderRepository;

    @Override
    public Iterable<Merchant> findAll() {
        return merchantRepository.findAll();
    }

    @Override
    public Optional<Merchant> findById(Long id) {
        return merchantRepository.findById(id);
    }

    @Override
    public Merchant save(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<Merchant> findMerchantByUserId(Long id) {
        return merchantRepository.findMerchantByUserId(id);
    }

    @Override
    public Optional<Merchant> findMerchantByUser_Id(Long userId) {
        return merchantRepository.findFirstByUser_Id(userId);
    }

    @Override
    public Iterable<DishDto> getAllDishDTO(Long id) {
        List<DishDto> dishDTOs = new ArrayList<>();
        Iterable<Dish> dishes = dishService.findAllByMerchant_Id(id);
        for (Dish dish : dishes) {
            List<Order> orders = (List<Order>) orderDetailService.findAllOrderByDishId(dish.getId());
            int orderQuantity = orders.size();
            DishDto dishDto = new DishDto();
            dishDto.setId(dish.getId());
            dishDto.setDishName(dish.getName());
            dishDto.setOrderQuantity(orderQuantity);
            dishDTOs.add(dishDto);
        }
        return dishDTOs;
    }

    @Override
    public Iterable<ICustomerDto> getAllCustomerDto(Long id) {
        return merchantRepository.findAllByCustomerDTOByMerchantId(id);
    }

    @Override
    public Iterable<OrderByQueryDto> finAllMerchantOrderByCustomerId(Long merchantId, Long userId) {
        return merchantRepository.finAllMerchantOrderByCustomerId(merchantId, userId);
    }

    @Override
    public Iterable<OrderByQueryDto> finAllOrderByMerchantIdInPeriod(Long id, LocalDate startTime, LocalDate endTime) {
        return merchantRepository.finAllOrderByMerchantIdInPeriod(id, startTime, endTime);
    }
}
