package com.codegym.service.delivery_info;

import com.codegym.model.entity.DeliveryInfo;
import com.codegym.model.entity.user.User;
import com.codegym.repository.IDeliveryInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeliveryInfoService implements IDeliveryInfoService {
    @Autowired
    IDeliveryInfoRepository deliveryInfoRepository;

    @Override
    public Iterable<DeliveryInfo> findAll() {
        return deliveryInfoRepository.findAll();
    }

    @Override
    public Optional<DeliveryInfo> findById(Long id) {
        return deliveryInfoRepository.findById(id);
    }

    @Override
    public DeliveryInfo save(DeliveryInfo deliveryInfo) {
        return deliveryInfoRepository.save(deliveryInfo);
    }

    @Override
    public void deleteById(Long id) {
        deliveryInfoRepository.deleteById(id);
    }

    @Override
    public Optional<DeliveryInfo> findDefaultDeliveryInfo(User user) {
        return deliveryInfoRepository.findFirstByUserAndSelectedIsTrue(user);
    }

    @Override
    public Iterable<DeliveryInfo> findOtherDeliveryInfos(User user) {
        return deliveryInfoRepository.findAllByUserAndSelectedIsFalse(user);
    }

    @Override
    public boolean setDeliveryInfoToSelected(Long userId, Long deliveryInfoId) {
        deliveryInfoRepository.makeAllDeliveryInfoUnselectedByUser(userId);
        int count = deliveryInfoRepository.setDeliveryInfoToSelected(deliveryInfoId);
        return count > 1;
    }


}
