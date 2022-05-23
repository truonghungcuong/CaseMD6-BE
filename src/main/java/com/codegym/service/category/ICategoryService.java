package com.codegym.service.category;

import com.codegym.model.entity.dish.category.Category;
import com.codegym.model.entity.dish.category.CategoryDTO;
import com.codegym.service.IGeneralService;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICategoryService extends IGeneralService<Category> {
    Iterable<CategoryDTO> getAllCategoryDTO();


    List<Category> findTop5Categories();
}