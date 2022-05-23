package com.codegym.model.entity;

import com.codegym.model.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private Date createDate;

    @ManyToOne
    private Coupon coupon;

    @OneToOne
    private DeliveryInfo deliveryInfo;

    private double serviceFee;

    private double shippingFee;

    private double discountAmount;

    private double totalFee;

    @Column(columnDefinition = "VARCHAR(1000)")
    private String restaurantNote;

    @Column(columnDefinition = "VARCHAR(1000)")
    private String shippingNote;

    @Column(columnDefinition = "TINYINT default 0")
    private int status;

}
