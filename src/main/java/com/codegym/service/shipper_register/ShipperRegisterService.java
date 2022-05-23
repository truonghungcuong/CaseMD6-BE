package com.codegym.service.shipper_register;

import com.codegym.model.entity.MerchantRegisterRequest;
import com.codegym.model.entity.ShipperRegisterRequest;
import com.codegym.model.entity.user.User;
import com.codegym.repository.IShipperRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShipperRegisterService implements IShipperRegisterService {
    @Autowired
    private IShipperRegisterRepository shipperRegisterRepository;

    @Override
    public Iterable<ShipperRegisterRequest> findAll() {
        return shipperRegisterRepository.findAll();
    }

    @Override
    public Optional<ShipperRegisterRequest> findById(Long id) {
        return shipperRegisterRepository.findById(id);
    }

    @Override
    public ShipperRegisterRequest save(ShipperRegisterRequest shipperRegisterRequest) {
        return shipperRegisterRepository.save(shipperRegisterRequest);
    }

    @Override
    public void deleteById(Long id) {
        shipperRegisterRepository.deleteById(id);
    }


    @Override
    public Optional<ShipperRegisterRequest> findByUserAndReviewed(User user, boolean reviewed) {
        return shipperRegisterRepository.findByUserAndReviewed(user,reviewed);
    }

    @Override
    public Iterable<ShipperRegisterRequest> findShipperByReviewed(boolean reviewed) {
        return shipperRegisterRepository.findShipperByReviewed(reviewed);
    }
}
