package com.codegym.repository;

import com.codegym.model.entity.dish.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select * from categories limit 5 offset 0", nativeQuery = true)
    List<Category> findTop5Categories();
}