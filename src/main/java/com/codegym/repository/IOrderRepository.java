package com.codegym.repository;

import com.codegym.model.dto.order.OrderByQueryDto;
import com.codegym.model.dto.order.OrderDto;
import com.codegym.model.dto.order.OrderDtoByOwner;
import com.codegym.model.entity.Order;
import com.codegym.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {
    Iterable<Order> findAllByUser(User user);

    Iterable<Order> findAllByUser_IdOrderByCreateDateDesc(Long userId);

    @Query(value = "select orders.id, u.full_name,d.name, (orders.total_fee - orders.service_fee - orders.shipping_fee) as orderPrice ,u.address, u.phone, orders.create_date,orders.status" +
            " from orders" +
            " join order_detail od on orders.id = od.order_id" +
            " join dishes d on d.id = od.dish_id" +
            " join merchants m on d.merchant_id = m.id" +
            " join users u on m.user_id = u.id" +
            " where u.id = :ownerId " +
            "group by orders.id " +
            "order by create_date desc ", nativeQuery = true)
    Iterable<OrderDtoByOwner> findOrderByOwnerIdOrderByCreateDateDesc(@Param(value = "ownerId") Long ownerId);

}
