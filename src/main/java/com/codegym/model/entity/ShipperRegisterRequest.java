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
@AllArgsConstructor
@NoArgsConstructor
public class ShipperRegisterRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @NotEmpty
    private String name;

    @Pattern(regexp = "^[0](\\+\\d{1,3}\\s?)?((\\(\\d{3}\\)\\s?)|(\\d{3})(\\s|-?))(\\d{3}(\\s|-?))(\\d{3})(\\s?(([E|e]xt[:|.|]?)|x|X)(\\s?\\d+))?"
    )
    private String phone;

    @NotEmpty
    private String licensePlates;

    @NotEmpty
    private String carName;

    @Column(columnDefinition = "boolean default false")
    private boolean reviewed;

    @Column(columnDefinition = "boolean default false")
    private boolean accept;
}
