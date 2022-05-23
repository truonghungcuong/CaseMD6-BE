package com.codegym.service.shipper;

import com.codegym.model.entity.Shipper;
import com.codegym.repository.IShipperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShipperService implements IShipperService {
    @Autowired
    private IShipperRepository shipperRepository;

    @Override
    public Iterable<Shipper> findAll() {
        return shipperRepository.findAll();
    }

    @Override
    public Optional<Shipper> findById(Long id) {
        return shipperRepository.findById(id);
    }

    @Override
    public Shipper save(Shipper shipper) {
        return shipperRepository.save(shipper);
    }

    @Override
    public void deleteById(Long id) {
        shipperRepository.deleteById(id);
    }
}
