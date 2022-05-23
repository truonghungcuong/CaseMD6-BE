package com.codegym.repository;

import com.codegym.model.entity.MerchantRegisterRequest;
import com.codegym.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMerchantRegisterRepository extends JpaRepository<MerchantRegisterRequest, Long> {
//    Optional<MerchantRegisterRequest> findByUserAndReviewedNot(User user);
    Optional<MerchantRegisterRequest> findByUserAndReviewed(User user, boolean reviewed);

    Iterable<MerchantRegisterRequest> findMerchantByReviewed(boolean reviewed);
}
