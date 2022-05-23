package com.codegym.service.order_detail;

import com.codegym.model.entity.Order;
import com.codegym.model.entity.OrderDetail;
import com.codegym.service.IGeneralService;

public interface IOrderDetailService extends IGeneralService<OrderDetail> {
    Iterable<OrderDetail> findAllByOrder(Order order);
    Iterable<OrderDetail> findAllByDishId(Long id);
    Iterable<Order> findAllOrderByDishId(Long id);

}
