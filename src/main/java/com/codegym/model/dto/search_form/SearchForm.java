package com.codegym.model.dto.search_form;

import com.codegym.model.entity.dish.category.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SearchForm {
    private String q;
    private List<Category> categories = new ArrayList<>();
    private int limit;
}
