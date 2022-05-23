package com.codegym.service.shipper_register;

import com.codegym.model.entity.MerchantRegisterRequest;
import com.codegym.model.entity.ShipperRegisterRequest;
import com.codegym.model.entity.user.User;
import com.codegym.service.IGeneralService;

import java.util.Optional;

public interface IShipperRegisterService extends IGeneralService<ShipperRegisterRequest> {
    Optional<ShipperRegisterRequest> findByUserAndReviewed(User user, boolean reviewed);

    Iterable<ShipperRegisterRequest> findShipperByReviewed(boolean reviewed);
}
