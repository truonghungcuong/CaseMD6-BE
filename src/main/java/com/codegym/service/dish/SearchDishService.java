package com.codegym.service.dish;

import com.codegym.model.dto.search_form.SearchForm;
import com.codegym.model.entity.dish.Dish;
import com.codegym.model.entity.dish.category.Category;
import com.codegym.repository.dish.IDishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchDishService {
    @Autowired
    private IDishRepository dishRepository;

    public Iterable<Dish> findAllDishes(int limit){
        return dishRepository.findAllDishes(limit);
    }

    public Iterable<Dish> searchByForm(SearchForm searchForm) {
        if (searchForm.getCategories().size() == 0) {
            return searchByNameOnly(searchForm.getQ(), searchForm.getLimit());
        }
        if (searchForm.getQ().isEmpty()){
            return searchByCategoriesOnly(searchForm.getCategories(), searchForm.getLimit());
        }
        return searchByNameAndCategories(searchForm.getQ(), searchForm.getCategories(), searchForm.getLimit());
    }

    public Iterable<Dish> searchByNameOnly(String name, int limit){
        if (name.isEmpty()){
            return findAllDishes(limit);
        }
        String namePattern = "%" + name + "%";
        return dishRepository.findAllDishesWithName(namePattern, limit);
    }

    public Iterable<Dish> searchByCategoriesOnly(List<Category> categories, int limit){
        String categoryIdList = generateCategoryIdListString(categories);
        Iterable<Dish> result = dishRepository.findDishesByCategoryIdList(categoryIdList, limit);
        return result;
    }

    public Iterable<Dish> searchByNameAndCategories(String name, List<Category> categories, int limit) {
        String namePattern = "%" + name + "%";
        String categoryIdList = generateCategoryIdListString(categories);
        return dishRepository.findDishesByNameAndCategoryIdList(namePattern, categoryIdList, limit);
    }

    public String generateCategoryIdListString(List<Category> categories) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < categories.size(); i++) {
            result.append(categories.get(i).getId().toString());
            if (i < categories.size() - 1) {
                result.append(",");
            }
        }
        return result.toString();
    }

}
