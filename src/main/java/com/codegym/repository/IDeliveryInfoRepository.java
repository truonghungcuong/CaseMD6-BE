package com.codegym.repository;

import com.codegym.model.entity.DeliveryInfo;
import com.codegym.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public interface IDeliveryInfoRepository extends JpaRepository<DeliveryInfo, Long> {

    Optional<DeliveryInfo> findFirstByUserAndSelectedIsTrue(User user);

    Iterable<DeliveryInfo> findAllByUserAndSelectedIsFalse(User user);

    @Modifying
    @Query(value = "update delivery_info"+
            " set selected = 1"+
            " where id = :id",nativeQuery = true)
    int setDeliveryInfoToSelected (@Param(value = "id") Long id );

    @Modifying
    @Query(value = "update delivery_info set selected = false where user_id = :userId", nativeQuery = true)
    int makeAllDeliveryInfoUnselectedByUser(@Param(value = "userId") Long userId);

}
