package com.codegym.controller.order;

import com.codegym.model.dto.order.OrderDto;
import com.codegym.model.entity.ErrorMessage;
import com.codegym.model.entity.Order;
import com.codegym.model.entity.user.User;
import com.codegym.service.cart.CartService;
import com.codegym.service.order.IOrderService;
import com.codegym.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/current-user")
public class CreateOrderController {
    @Autowired
    IUserService userService;

    @Autowired
    IOrderService orderService;

    @Autowired
    CartService cartService;

    // Input 1 orderDto => tạo vào lưu order vào DB
    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByUsername(principal.getName()).get();

        if (currentUser == null) {
            ErrorMessage errorMessage = new ErrorMessage("Người dùng chưa đăng nhập");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        if (orderDto.getCart().getCartDetails().size() == 0) {
            ErrorMessage errorMessage = new ErrorMessage("Giỏ hàng trống");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        Order order = cartService.saveOrderToDB(currentUser, orderDto);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }


}
