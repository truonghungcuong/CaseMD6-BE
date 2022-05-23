package com.codegym.service.category;

import com.codegym.model.entity.dish.category.Category;
import com.codegym.model.entity.dish.category.CategoryDTO;
import com.codegym.repository.ICategoryRepository;
import com.codegym.service.dish.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService{
    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private IDishService dishService;

    @Override
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Iterable<CategoryDTO> getAllCategoryDTO() {
        Iterable<Category> categories = findAll();
        List<CategoryDTO> categoryDTOs = new ArrayList<>();

        categories.forEach(
                category -> {
                    int count = dishService.countDishByCategoriesIsContaining(category);
                    CategoryDTO categoryDTO = new CategoryDTO();
                    categoryDTO.setId(category.getId());
                    categoryDTO.setName(category.getName());
                    categoryDTO.setNumberOfDishes(count);
                    categoryDTOs.add(categoryDTO);
                }
        );
        return categoryDTOs;
    }

    @Override
    public List<Category> findTop5Categories() {
        return categoryRepository.findTop5Categories();
    }
}
