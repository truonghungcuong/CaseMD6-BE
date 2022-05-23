package com.codegym.service.order;

import com.codegym.model.dto.order.OrderByQueryDto;
import com.codegym.model.dto.order.OrderDto;
import com.codegym.model.dto.order.OrderDtoByOwner;
import com.codegym.model.entity.Order;
import com.codegym.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface IOrderService extends IGeneralService<Order> {

    OrderDto getOrderDto(Long orderId);

    List<OrderDto> findAllOrderDtoByUserId(Long userId);

    Iterable<Order> findAllByUserId(Long id);

    Iterable<OrderDtoByOwner> findAllOrderDtoByOwnerId(Long ownerId);

}
