package com.codegym.controller;

import com.codegym.model.entity.dish.category.Category;
import com.codegym.model.entity.dish.category.CategoryDTO;
import com.codegym.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> findAllCategories(){
        Iterable<CategoryDTO> categoryDTOs = categoryService.getAllCategoryDTO();
        return new ResponseEntity<>(categoryDTOs, HttpStatus.OK);
    }

    @GetMapping("/top-five")
    public ResponseEntity<?> findTopFiveCategories(){
        List<Category> top5Categories = categoryService.findTop5Categories();
        return new ResponseEntity<>(top5Categories, HttpStatus.OK);
    }
}
