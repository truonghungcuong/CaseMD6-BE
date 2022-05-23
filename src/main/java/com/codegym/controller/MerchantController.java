package com.codegym.controller;

import com.codegym.model.dto.customer.ICustomerDto;
import com.codegym.model.dto.dish.DishDto;
import com.codegym.model.dto.order.OrderByQueryDto;
import com.codegym.model.dto.order.OrderDto;
import com.codegym.model.dto.order.OrderDtoByOwner;
import com.codegym.model.entity.ErrorMessage;
import com.codegym.model.entity.Merchant;
import com.codegym.model.entity.Order;
import com.codegym.model.entity.dish.Dish;
import com.codegym.model.entity.dish.DishForm;
import com.codegym.model.entity.user.User;
import com.codegym.service.dish.IDishService;
import com.codegym.service.merchant.IMerchantService;
import com.codegym.service.order.IOrderService;
import com.codegym.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/merchants")
public class MerchantController {
    @Autowired
    private IMerchantService merchantService;
    @Autowired
    private IDishService dishService;

    @Autowired
    IUserService userService;

    @Autowired
    IOrderService orderService;

    @Value("${file-upload}")
    private String uploadPath;

    @GetMapping
    public ResponseEntity<Iterable<Merchant>> findAllMerchant() {
        Iterable<Merchant> merchants = merchantService.findAll();
        return new ResponseEntity<>(merchants, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Merchant> findById(@PathVariable Long id) {
        Optional<Merchant> merchantOptional = merchantService.findById(id);
        if (!merchantOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(merchantOptional.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Merchant> updateMerchant(@PathVariable Long id, @RequestBody Merchant newMerchant) {
        Optional<Merchant> merchantOptional = merchantService.findById(id);
        if (!merchantOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        newMerchant.setId(id);
        return new ResponseEntity<>(merchantService.save(newMerchant), HttpStatus.OK);
    }


//    @GetMapping("/{id}/dishes")
//    public ResponseEntity<Iterable<Dish>> findAllMerchantDishes(@PathVariable Long id) {
//        Iterable<Dish> dishes = dishService.findAllByMerchantId(id);
//        return new ResponseEntity<>(dishes, HttpStatus.OK);
//    }

    @PutMapping("/editMerchant/{id}")
    public ResponseEntity<Merchant> updateInformationMerchant(@PathVariable Long id, @RequestBody Merchant merchant) {
        Optional<Merchant> merchantOptional = merchantService.findById(id);
        if (!merchantOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Merchant newMerchant = merchantOptional.get();
        newMerchant.setId(id);
        newMerchant.setName(merchant.getName());
        newMerchant.setDescription(merchant.getDescription());
        newMerchant.setAddress(merchant.getAddress());
        newMerchant.setPhone(merchant.getPhone());
        newMerchant.setOpenTime(merchant.getOpenTime());
        newMerchant.setCloseTime(merchant.getCloseTime());
        return new ResponseEntity<>(merchantService.save(newMerchant), HttpStatus.OK);
    }

//    @GetMapping("/{id}/dishes")
//    public ResponseEntity<Iterable<Dish>> findAllMerchantDishes(@PathVariable Long id) {
//        Iterable<Dish> dishes = dishService.findAllByMerchantId(id);
//        return new ResponseEntity<>(dishes, HttpStatus.OK);
//    }

    @GetMapping("/user/{userId}/merchant/dishes")
    public ResponseEntity<?> findMerchantByUserId(@PathVariable Long userId) {
        Optional<Merchant> merchantOptional = merchantService.findMerchantByUserId(userId);
        if (!merchantOptional.isPresent()) {
            ErrorMessage errorMessage = new ErrorMessage("Cửa hàng không tồn tại");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        Iterable<Dish> dishes = dishService.findAllByMerchant(merchantOptional.get());
        return new ResponseEntity<>(dishes, HttpStatus.OK);
    }

    @GetMapping("/my-merchant")
    public ResponseEntity<?> getCurrentUserMerchant() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByUsername(principal.getName()).get();

        if (currentUser == null) {
            ErrorMessage errorMessage = new ErrorMessage("Người dùng chưa đăng nhập");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        Optional<Merchant> findMerchant = merchantService.findMerchantByUser_Id(currentUser.getId());
        if (!findMerchant.isPresent()) {
            ErrorMessage errorMessage = new ErrorMessage("Tài khoản này không phải là chủ cửa hàng");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(findMerchant.get(), HttpStatus.OK);
    }

    @PostMapping("/dish/create")
    public ResponseEntity<?> saveDish(@RequestBody DishForm dishForm) {
        Dish dish = new Dish();
        dish.setId(dishForm.getId());
        dish.setName(dishForm.getName());
        dish.setCategories(dishForm.getCategories());
        dish.setPrice(dishForm.getPrice());
        dish.setMerchant(dishForm.getMerchant());
        dish.setDescription(dishForm.getDescription());
//            dish.setImage(fileName);
        return new ResponseEntity<>(dishService.save(dish), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/get-dishes-dto")
    public ResponseEntity<?> findAllOrderByDish(@PathVariable Long id) {
        Iterable<DishDto> dishDTOs = merchantService.getAllDishDTO(id);
        return new ResponseEntity<>(dishDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}/get-users-dto")
    public ResponseEntity<?> findAllOrderByCustomer(@PathVariable Long id) {
        Iterable<ICustomerDto> customerDTOs = merchantService.getAllCustomerDto(id);
        return new ResponseEntity<>(customerDTOs, HttpStatus.OK);
    }

    @GetMapping("/{merchantId}/users/{userId}/orders")
    public ResponseEntity<?> finAllMerchantOrderByCustomerId(@PathVariable Long merchantId, @PathVariable Long userId) {
        Iterable<OrderByQueryDto> orderByQueryDTOs = merchantService.finAllMerchantOrderByCustomerId(merchantId, userId);
        return new ResponseEntity<>(orderByQueryDTOs, HttpStatus.OK);
    }


    @GetMapping ("/{id}/orders")
    public ResponseEntity<?> finAllOrderByMerchantIdInPeriod (@PathVariable Long id, @RequestParam (name = "startTime") Optional<String> startTime, @RequestParam (name = "endTime") Optional<String> endTime) throws ParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startTime.get(), dtf);
        LocalDate end = LocalDate.parse(endTime.get(), dtf);
        end = end.plusDays(1);
        Iterable<OrderByQueryDto> orderByQueryDTOs = merchantService.finAllOrderByMerchantIdInPeriod(id, start, end);
        return new ResponseEntity<>(orderByQueryDTOs, HttpStatus.OK);
    }

    @GetMapping("/owners/{ownerId}/orders")
    public ResponseEntity<?> getAllOrderByMerchantId(@PathVariable Long ownerId) {
        Iterable<OrderDtoByOwner> orderDtos = orderService.findAllOrderDtoByOwnerId(ownerId);
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }

    @PutMapping("/dish/{id}")
    public ResponseEntity<?> updateMerchantDishById(@PathVariable Long id, @ModelAttribute DishForm dishForm) {
        Optional<Dish> dishOptional = dishService.findById(id);
        if (!dishOptional.isPresent()) {
            ErrorMessage errorMessage = new ErrorMessage("Món ăn này không tồn tại");
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        } else {
            Dish oldDish = dishOptional.get();
            oldDish.setName(dishForm.getName());
            oldDish.setPrice(dishForm.getPrice());
            oldDish.setCategories(dishForm.getCategories());
            oldDish.setDescription(dishForm.getDescription());

            MultipartFile img = dishForm.getImage();
            if (img != null && img.getSize() != 0) {
                String fileName = img.getOriginalFilename();
                long currentTime = System.currentTimeMillis();
                fileName = currentTime + "_" + fileName;
                try {
                    FileCopyUtils.copy(img.getBytes(), new File(uploadPath + fileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                oldDish.setImage(fileName);
            }
            return new ResponseEntity<>(dishService.save(oldDish), HttpStatus.OK);
        }
    }


    @GetMapping("order/{orderId}")
    public ResponseEntity<?> findOrderByOrderId(@PathVariable Long orderId) {
        OrderDto orderDto = orderService.getOrderDto(orderId);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }


    // test uploadfile create dish
    @PostMapping("/dish/create1")
    public ResponseEntity<?> saveDishImg(@ModelAttribute DishForm dishForm) {
        MultipartFile img = dishForm.getImage();
        Dish newDish = new Dish();
        if (img != null && img.getSize() != 0) {
            String fileName = img.getOriginalFilename();
            long currentTime = System.currentTimeMillis();
            fileName = currentTime + fileName;
            newDish.setImage(fileName);
            try {
                FileCopyUtils.copy(img.getBytes(), new File(uploadPath + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        newDish.setName(dishForm.getName());
        newDish.setPrice(dishForm.getPrice());
        newDish.setCategories(dishForm.getCategories());
        newDish.setMerchant(dishForm.getMerchant());
        newDish.setDescription(dishForm.getDescription());
        newDish.setSold(0L);
        return new ResponseEntity<>(dishService.save(newDish), HttpStatus.OK);
    }
}