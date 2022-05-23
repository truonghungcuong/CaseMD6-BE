package com.codegym.controller;

import com.codegym.model.ShipperRegisterForm;
import com.codegym.model.entity.*;
import com.codegym.model.entity.user.Role;
import com.codegym.model.entity.user.User;
import com.codegym.service.shipper.IShipperService;
import com.codegym.service.shipper_register.IShipperRegisterService;
import com.codegym.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/registerShipper")
public class RegisterShipperController {
    @Autowired
    private IShipperRegisterService shipperRegisterService;

    @Autowired
    private IShipperService shipperService;

    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<?> registerShipper(@RequestBody ShipperRegisterForm shipperRegisterForm) {

        User user = shipperRegisterForm.getUser();

        Optional<ShipperRegisterRequest> shipperRegisterRequest = shipperRegisterService.findByUserAndReviewed(user, false);
        if (shipperRegisterRequest.isPresent()) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage("Không thể tạo thêm yêu cầu: đã tồn tại yêu cầu đang chờ xét duyệt");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        ShipperRegisterRequest shipper = new ShipperRegisterRequest();
        shipper.setUser(shipperRegisterForm.getUser());
        shipper.setName(shipperRegisterForm.getName());
        shipper.setPhone(shipperRegisterForm.getPhone());
        shipper.setCarName(shipperRegisterForm.getCarName());
        shipper.setLicensePlates(shipperRegisterForm.getLicensePlates());
        shipper = shipperRegisterService.save(shipper);
        return new ResponseEntity<>(shipper, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> findAllShipperRegisterRequest() {
        Iterable<ShipperRegisterRequest> shipperRegisterRequests = shipperRegisterService.findShipperByReviewed(false);
        return new ResponseEntity<>(shipperRegisterRequests, HttpStatus.OK);
    }
    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptRegisterRequest(@PathVariable Long id) {
        Optional<ShipperRegisterRequest> findShipperRegisterRequest = shipperRegisterService.findById(id);
        if (!findShipperRegisterRequest.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ShipperRegisterRequest srr = findShipperRegisterRequest.get();

        Shipper shipper = new Shipper();
        shipper.setUser(srr.getUser());
        shipper.setName(srr.getName());
        shipper.setPhone(srr.getPhone());
        shipper.setLicensePlates(srr.getPhone());
        shipper.setCarName(srr.getPhone());



        User user = srr.getUser();
        Role role = new Role(2L, Role.ROLE_SHIPPER);
        user.setRole(role);

        srr.setReviewed(true);
        srr.setAccept(true);

        //luu thay doi vao DB
        shipperService.save(shipper);
        userService.save(user);
        shipperRegisterService.save(srr);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/refuse/{id}")
    public ResponseEntity<?> refuseRegisterRequest(@PathVariable Long id) {
        Optional<ShipperRegisterRequest> findShipperRegisterRequest = shipperRegisterService.findById(id);
        if (!findShipperRegisterRequest.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ShipperRegisterRequest srr = findShipperRegisterRequest.get();
        srr.setReviewed(true);
        srr.setAccept(false);
        shipperRegisterService.save(srr);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
