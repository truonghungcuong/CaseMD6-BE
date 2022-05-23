package com.codegym.repository;

import com.codegym.model.dto.customer.ICustomerDto;
import com.codegym.model.dto.order.OrderByQueryDto;
import com.codegym.model.entity.Merchant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface IMerchantRepository extends PagingAndSortingRepository<Merchant,Long> {

    Optional<Merchant> findMerchantByUserId(Long id);

    Optional<Merchant> findFirstByUser_Id(Long userId);

    @Query(value = "select u.id, u.full_name, u.phone, u.address, u.email, count(distinct o.id) as orderQuantity" +
            " from merchants join dishes d on merchants.id = d.merchant_id" +
            " join order_detail od on d.id = od.dish_id join orders o on od.order_id = o.id" +
            " join users u on o.user_id = u.id where merchant_id = :merchantId group by u.id", nativeQuery = true)
    Iterable<ICustomerDto> findAllByCustomerDTOByMerchantId (@Param(value = "merchantId") Long merchantId);

    @Query(value = "select o.*, count(o.id) as orderQuantity" +
            " from merchants join dishes d on merchants.id = d.merchant_id" +
            " join order_detail od on d.id = od.dish_id join orders o on od.order_id = o.id" +
            " join users u on o.user_id = u.id where merchant_id = :merchantId and u.id = :userId group by o.id", nativeQuery = true)
    Iterable<OrderByQueryDto> finAllMerchantOrderByCustomerId (@Param(value = "merchantId") Long merchantId,  @Param(value = "userId")Long userId);

    @Query(value = "select distinct o.*, u.full_name, u.email,u.address, u.phone from orders o" +
            " join users u on o.user_id = u.id" +
            " join order_detail od on o.id = od.order_id" +
            " join dishes d on od.dish_id = d.id" +
            " join merchants m on d.merchant_id = m.id where m.id = ?1 and o.create_date between ?2 AND ?3 AND o.status >0" +
            " order by o.create_date desc", nativeQuery = true)
    Iterable<OrderByQueryDto> finAllOrderByMerchantIdInPeriod (Long id, LocalDate startTime, LocalDate endTime);
}
