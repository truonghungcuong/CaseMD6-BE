package com.codegym.model.entity.user;

import com.codegym.model.entity.dish.Dish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Không được để trống")
    @Column(unique = true)
    private String username;

    @Email
    @Column(unique = true)
    @NotBlank(message = "Không được để trống")
    private String email;
    @NotEmpty(message = "Không được để trống")
    @Size(min = 8)
    private String password;
    @Pattern(regexp = "^[0](\\\\+\\\\d{1,3}\\\\s?)?((\\\\(\\\\d{3}\\\\)\\\\s?)|(\\\\d{3})(\\\\s|-?))(\\\\d{3}(\\\\s|-?))(\\\\d{3})(\\\\s?(([E|e]xt[:|.|]?)|x|X)(\\\\s?\\\\d+))?")
    private String phone;

    @Column(columnDefinition = "varchar(255) default 'user-default.jpg'")
    private String image;

    @ManyToOne
    private Role role;

    @OneToMany
    private List<Dish> favorite;

    private String fullName;

    private String address;
}
