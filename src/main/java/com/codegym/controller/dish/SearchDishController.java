package com.codegym.controller.dish;

import com.codegym.model.dto.search_form.SearchForm;
import com.codegym.model.entity.dish.Dish;
import com.codegym.service.dish.SearchDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/dishes")
public class SearchDishController {
    @Autowired
    SearchDishService searchDishService;

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchForm searchForm){
        Iterable<Dish> dishes =  searchDishService.searchByForm(searchForm);
        return new ResponseEntity<>(dishes, HttpStatus.OK);
    }

}
