package com.codegym.controller;

import com.codegym.model.entity.DeliveryInfo;
import com.codegym.model.entity.user.User;
import com.codegym.service.delivery_info.IDeliveryInfoService;
import com.codegym.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
public class DeliveryInfoController {
    @Autowired
    IUserService userService;

    @Autowired
    IDeliveryInfoService deliveryInfoService;


    @GetMapping("/{userId}/default-delivery-info")
    public ResponseEntity<?> findDefaultDeliveryInfo(@PathVariable Long userId) {
        Optional<User> findUser = userService.findById(userId);
        if (!findUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = findUser.get();
        Optional<DeliveryInfo> findDefaultDeliveryInfo = deliveryInfoService.findDefaultDeliveryInfo(user);
        DeliveryInfo deliveryInfo = null;
        if (findDefaultDeliveryInfo.isPresent()) {
            deliveryInfo = findDefaultDeliveryInfo.get();
        }

        return new ResponseEntity<>(deliveryInfo, HttpStatus.OK);
    }

    @GetMapping("/{userId}/other-delivery-infos")
    public ResponseEntity<?> findOtherDeliveryInfos(@PathVariable Long userId) {
        Optional<User> findUser = userService.findById(userId);
        if (!findUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = findUser.get();
        Iterable<DeliveryInfo> deliveryInfos = deliveryInfoService.findOtherDeliveryInfos(user);
        return new ResponseEntity<>(deliveryInfos, HttpStatus.OK);
    }

    @GetMapping("/{userId}/{deliveryInfoId}/make-default")
    public ResponseEntity<?> setDeliveryInfoToSelected(@PathVariable Long userId, @PathVariable Long deliveryInfoId) {
        Optional<User> findUser = userService.findById(userId);
        if (!findUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<DeliveryInfo> findDeliveryInfo = deliveryInfoService.findById(deliveryInfoId);
        if (!findDeliveryInfo.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
       deliveryInfoService.setDeliveryInfoToSelected(userId, deliveryInfoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{deliveryInfoId}")
    public ResponseEntity<?> saveDeliveryInfo(@PathVariable Long deliveryInfoId, @RequestBody DeliveryInfo newDeliveryInfo){
        Optional<DeliveryInfo> findDeliveryInfo = deliveryInfoService.findById(deliveryInfoId);
        if (!findDeliveryInfo.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        DeliveryInfo oldDeliveryInfo = findDeliveryInfo.get();
        oldDeliveryInfo.setName(newDeliveryInfo.getName());
        oldDeliveryInfo.setPhone(newDeliveryInfo.getPhone());
        oldDeliveryInfo.setAddress(newDeliveryInfo.getAddress());
        return new ResponseEntity<>(deliveryInfoService.save(oldDeliveryInfo),HttpStatus.OK);
    }

    @PostMapping("/delivery/create")
    public ResponseEntity<?> createDeliveryInfo(@RequestBody DeliveryInfo deliveryInfo){
          DeliveryInfo newDeliveryInfo = new DeliveryInfo();
        newDeliveryInfo.setName(deliveryInfo.getName());
        newDeliveryInfo.setAddress(deliveryInfo.getAddress());
        newDeliveryInfo.setPhone(deliveryInfo.getPhone());
        newDeliveryInfo.setUser(deliveryInfo.getUser());
        return new ResponseEntity<>(deliveryInfoService.save(newDeliveryInfo),HttpStatus.OK);
    }
}
