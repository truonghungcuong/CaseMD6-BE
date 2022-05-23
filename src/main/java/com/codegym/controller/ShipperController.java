package com.codegym.controller;

import com.codegym.model.entity.Merchant;
import com.codegym.model.entity.Shipper;
import com.codegym.service.shipper.IShipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/shippers")
public class ShipperController {
    @Autowired
    private IShipperService shipperService;

    @GetMapping
    public ResponseEntity<Iterable<Shipper>> findAllShipper() {
        Iterable<Shipper> shippers = shipperService.findAll();
        return new ResponseEntity<>(shippers, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Shipper> updateMerchant(@PathVariable Long id, @RequestBody Shipper newShipper) {
        Optional<Shipper> shipperOptional = shipperService.findById(id);
        if (!shipperOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        newShipper.setId(id);
        return new ResponseEntity<>(shipperService.save(newShipper), HttpStatus.OK);
    }
}
