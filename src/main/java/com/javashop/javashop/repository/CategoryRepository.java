package com.javashop.javashop.repository;

import com.javashop.javashop.model.Category;
import com.javashop.javashop.model.ComplaintType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByIdIn(List<Integer> id, Pageable pageable);
    Long countByIdIn(List<Integer> id);

    Long countByProducts_Id(Integer productID);

    List<Category> findByProducts_Id(Integer productID, Pageable pageable);
}