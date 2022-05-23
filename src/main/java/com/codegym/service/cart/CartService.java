package com.codegym.service.cart;

import com.codegym.model.dto.cart.CartDetailDto;
import com.codegym.model.dto.cart.CartDto;
import com.codegym.model.dto.order.OrderDto;
import com.codegym.model.entity.*;
import com.codegym.model.entity.dish.Dish;
import com.codegym.model.entity.user.User;
import com.codegym.repository.ICartRepository;
import com.codegym.service.cart_detail.ICartDetailService;
import com.codegym.service.dish.IDishService;
import com.codegym.service.merchant.IMerchantService;
import com.codegym.service.order.IOrderService;
import com.codegym.service.order_detail.IOrderDetailService;
import com.codegym.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {
    @Autowired
    ICartRepository cartRepository;

    @Autowired
    ICartDetailService cartDetailService;

    @Autowired
    IOrderService orderService;

    @Autowired
    IOrderDetailService orderDetailService;

    @Autowired
    IDishService dishService;

    @Autowired
    IUserService userService;

    @Autowired
    IMerchantService merchantService;


    @Override
    public Iterable<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void deleteById(Long id) {
        cartRepository.deleteById(id);
    }


    @Override
    public Optional<Cart> findCartByUserAndMerchant(User user, Merchant merchant) {
        return cartRepository.findCartByUserAndMerchant(user, merchant);
    }

    @Override
    public Iterable<Cart> findAllCartByUser(User user) {
        return cartRepository.findCartByUser(user);
    }

    @Override
    public Cart createCartWithUserAndMerchant(User user, Merchant merchant) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setMerchant(merchant);
        return cart;
    }

    @Override
    public CartDto getCartDtoByUserAndMerchant(User user, Merchant merchant) {
        Optional<Cart> findCart = cartRepository.findCartByUserAndMerchant(user, merchant);
        Cart cart;
        if (findCart.isPresent()) {
            cart = findCart.get();
        } else {
            cart = new Cart();
            cart.setUser(user);
            cart.setMerchant(merchant);
            cart = this.save(cart);
        }

        CartDto cartDto = makeCartDtoFromCart(cart);
        return cartDto;
    }

    @Override
    public List<CartDto> getAllCartDtoByUser(User user) {
        Iterable<Cart> carts = findAllCartByUser(user);
        List<CartDto> cartDtos = new ArrayList<>();
        for (Cart cart : carts) {
            List<CartDetail> cartDetails = (List<CartDetail>) cartDetailService.findAllByCart(cart);
            if (cartDetails.size() == 0) {
                cartRepository.deleteById(cart.getId());
                break;
            }
            CartDto cartDto = makeCartDtoFromCart(cart);
            cartDtos.add(cartDto);
        }
        return cartDtos;
    }

    @Override
    public CartDto addDishToCart(User user, CartDetailDto cartDetailDto) {
        Dish dish = cartDetailDto.getDish();
        Merchant merchant = dish.getMerchant();
        int quantity = cartDetailDto.getQuantity();
        CartDto cartDto = getCartDtoByUserAndMerchant(user, merchant);
        Long cartId = cartDto.getId();
        Cart cart = findById(cartId).get();

        Optional<CartDetail> findCartDetail = cartDetailService.findByCartAndDish(cart, dish);
        CartDetail cartDetail;
        if (findCartDetail.isPresent()) {
            cartDetail = findCartDetail.get();
            cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
        } else {
            cartDetail = new CartDetail();
            cartDetail.setDish(dish);
            cartDetail.setCart(cart);
            cartDetail.setQuantity(quantity);
        }
        cartDetailService.save(cartDetail);
        cartDto = getCartDtoByUserAndMerchant(user, merchant);
        return cartDto;
    }

    @Override
    public boolean changeDishQuantityInCart(Cart cart, Dish dish, int amount) {
        Optional<CartDetail> findCartDetail = cartDetailService.findByCartAndDish(cart, dish);
        CartDetail cartDetail;
        if (findCartDetail.isPresent()){
            cartDetail = findCartDetail.get();
            cartDetail.setQuantity(cartDetail.getQuantity() + amount);
        } else {
            if (amount < 0) return false;
            cartDetail = new CartDetail();
            cartDetail.setCart(cart);
            cartDetail.setDish(dish);
            cartDetail.setQuantity(amount);
        }
        if (cartDetail.getQuantity() < 1){
            cartDetailService.deleteById(cartDetail.getId());
        } else {
            cartDetailService.save(cartDetail);
        }
        return true;
    }


    public CartDto makeCartDtoFromCart(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cart.setUser(cart.getUser());
        cartDto.setMerchant(cart.getMerchant());

        Iterable<CartDetail> cartDetails = cartDetailService.findAllByCart(cart);
        cartDetails.forEach(
                cartDetail -> {
                    CartDetailDto cartDetailDto = new CartDetailDto();
                    cartDetailDto.setDish(cartDetail.getDish());
                    cartDetailDto.setQuantity(cartDetail.getQuantity());
                    cartDto.addCartDetailDto(cartDetailDto);
                }
        );
        return cartDto;
    }


    public Order saveOrderToDB(User user, OrderDto orderDto) {
        Order order = new Order();
        order.setUser(user);

        DeliveryInfo deliveryInfo = orderDto.getDeliveryInfo();
        order.setDeliveryInfo(deliveryInfo);

        order = orderService.save(order); // để tạo order.id => để các order detail có thể link đến

        CartDto cartDto = orderDto.getCart();

        // lưu order details
        // tăng thuộc tính sold của dish và lưu vào DB
        List<CartDetailDto> cartDetailDtos = cartDto.getCartDetails();
        for (CartDetailDto cartDetailDto : cartDetailDtos) {
            OrderDetail orderDetail = new OrderDetail();
            Dish dish = cartDetailDto.getDish();
            int quantity = cartDetailDto.getQuantity();
            orderDetail.setOrder(order);
            orderDetail.setDish(dish);
            orderDetail.setQuantity(quantity);
            orderDetailService.save(orderDetail);

            dish.setSold(dish.getSold() + quantity);
            dishService.save(dish);
        }

        order.setServiceFee(cartDto.getServiceFee());
        order.setShippingFee(cartDto.getShippingFee());
        order.setDiscountAmount(cartDto.getDiscountAmount());
        order.setTotalFee(cartDto.getTotalFee());

        order.setRestaurantNote("");
        order.setShippingNote("");
        order.setCoupon(null);

        order = orderService.save(order);
        order.setCreateDate(new Date());
        emptyCartById(orderDto.getCart().getId());
        return orderService.save(order);
    }

    public void emptyCartById(Long cartId){
        Optional<Cart> findCart = findById(cartId);
        if (findCart.isPresent()) {
            cartDetailService.deleteAllByCart(findCart.get());
        }
    };

}
