package com.codegym.controller;

import com.codegym.model.MerchantRegisterForm;
import com.codegym.model.entity.ErrorMessage;

import com.codegym.model.entity.Merchant;
import com.codegym.model.entity.MerchantRegisterRequest;

import com.codegym.model.entity.user.Role;
import com.codegym.model.entity.user.User;
import com.codegym.service.IMerchantRegisterService;
import com.codegym.service.merchant.IMerchantService;
import com.codegym.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import java.util.Optional;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/registerMerchant")
public class RegisterMerchantController {

    @Autowired
    IMerchantRegisterService merchantRegisterService;

    @Autowired
    IMerchantService merchantService;

    @Autowired
    IUserService userService;

    @PostMapping
    public ResponseEntity<?> registerMerchant(@RequestBody MerchantRegisterForm merchantRegisterForm) {
//

        User user = merchantRegisterForm.getUser();


        Optional<MerchantRegisterRequest> merchantRegisterRequest = merchantRegisterService.findByUserAndReviewed(user, false);
        if (merchantRegisterRequest.isPresent()) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage("Không thể tạo thêm yêu cầu: đã tồn tại yêu cầu đang chờ xét duyệt");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        MerchantRegisterRequest merchant = new MerchantRegisterRequest();
        merchant.setUser(merchantRegisterForm.getUser());
        merchant.setName(merchantRegisterForm.getName());
        merchant.setDescription(merchantRegisterForm.getDescription());
        merchant.setAddress(merchantRegisterForm.getAddress());
        merchant.setPhone(merchantRegisterForm.getPhone());
        merchant.setOpenTime(merchantRegisterForm.getOpenTime());
        merchant.setCloseTime(merchantRegisterForm.getCloseTime());
        merchant = merchantRegisterService.save(merchant);
        return new ResponseEntity<>(merchant, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> findAllMerchantRegisterRequest() {
        Iterable<MerchantRegisterRequest> merchantRegisterRequest = merchantRegisterService.findMerchantByReviewed(false);
        return new ResponseEntity<>(merchantRegisterRequest, HttpStatus.OK);
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptRegisterRequest(@PathVariable Long id) {
        Optional<MerchantRegisterRequest> findMerchantRegisterRequest = merchantRegisterService.findById(id);
        if (!findMerchantRegisterRequest.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MerchantRegisterRequest mrr = findMerchantRegisterRequest.get();

        // tao dt merchant moi va luu db
        Merchant merchant = new Merchant();
        merchant.setUser(mrr.getUser());
        merchant.setName(mrr.getName());
        merchant.setDescription(mrr.getDescription());
        merchant.setAddress(mrr.getAddress());
        merchant.setPhone(mrr.getPhone());
        merchant.setOpenTime(mrr.getOpenTime());
        merchant.setCloseTime(mrr.getCloseTime());
        merchant.setAvatar("merchant-avatar-default.jpg");
        merchant.setImageBanner("merchant-imageBanner-default.jpg");
        // sua role user thanh role merchant
        User user = mrr.getUser();
        Role role = new Role(2L, Role.ROLE_MERCHANT);
        user.setRole(role);

        // thay doi merchanRegisterRequest ==> reviewed=true, accepted = true
        mrr.setReviewed(true);
        mrr.setAccept(true);

        //luu thay doi vao DB
        merchantService.save(merchant);
        userService.save(user);
        merchantRegisterService.save(mrr);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/refuse/{id}")
    public ResponseEntity<?> refuseRegisterRequest(@PathVariable Long id) {
        Optional<MerchantRegisterRequest> findMerchantRegisterRequest = merchantRegisterService.findById(id);
        if (!findMerchantRegisterRequest.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MerchantRegisterRequest mrr = findMerchantRegisterRequest.get();
        mrr.setReviewed(true);
        mrr.setAccept(false);
        merchantRegisterService.save(mrr);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
