package com.codegym.repository;

import com.codegym.model.entity.Order;
import com.codegym.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    Iterable<OrderDetail> findAllByOrder(Order order);
    Iterable<OrderDetail> findAllByDishId(Long id);
}
