package com.codegym.model.entity.dish;

import com.codegym.model.entity.dish.category.Category;
import com.codegym.model.entity.Merchant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishForm {
    private Long id;

    private String name;

    private double price;

    private List<Category> categories;

    private Merchant merchant;

    private String description;

    private MultipartFile image;

    private boolean ceased;
}