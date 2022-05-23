package com.codegym.repository;

import com.codegym.model.entity.ShipperRegisterRequest;
import com.codegym.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IShipperRegisterRepository extends JpaRepository<ShipperRegisterRequest, Long> {
    Optional<ShipperRegisterRequest> findByUserAndReviewed(User user, boolean reviewed);

    Iterable<ShipperRegisterRequest> findShipperByReviewed(boolean reviewed);
}
