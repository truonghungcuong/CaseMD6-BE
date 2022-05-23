package com.codegym.service.delivery_info;

import com.codegym.model.entity.DeliveryInfo;
import com.codegym.model.entity.user.User;
import com.codegym.service.IGeneralService;

import java.util.Optional;

public interface IDeliveryInfoService extends IGeneralService<DeliveryInfo> {

    Optional<DeliveryInfo> findDefaultDeliveryInfo(User user);

    Iterable<DeliveryInfo> findOtherDeliveryInfos(User user);

    boolean setDeliveryInfoToSelected(Long userId, Long deliveryInfoId);
}
