package com.codegym.model.entity;


import com.codegym.model.entity.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "merchants")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
    private String address;
    @NotEmpty
    @Pattern(regexp = "^[0](\\+\\d{1,3}\\s?)?((\\(\\d{3}\\)\\s?)|(\\d{3})(\\s|-?))(\\d{3}(\\s|-?))(\\d{3})(\\s?(([E|e]xt[:|.|]?)|x|X)(\\s?\\d+))?")
    private String phone;

    @Column(columnDefinition = "TIME")
    private String openTime;

    @Column(columnDefinition = "TIME")
    private String closeTime;

    // anh chup chung nhan Ve sinh an toan thuc pham
    private String vsattp;

    @Column(name = "isActive", columnDefinition = "boolean default true")
    private boolean isActive;

    private String avatar;

    private String imageBanner;
}
