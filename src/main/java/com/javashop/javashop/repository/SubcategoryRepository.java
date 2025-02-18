package com.javashop.javashop.repository;

import com.javashop.javashop.model.Complaint;
import com.javashop.javashop.model.Subcategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {
    List<Subcategory> findByIdIn(List<Integer> id, Pageable pageable);
    List<Subcategory> findByCategoryId(Integer id, Pageable pageable);
    List<Subcategory> findByCategoryIdAndIdIn(Integer category_id, List<Integer> id, Pageable pageable);
    Long countByIdIn(List<Integer> id);

    Long countByProducts_Id(Integer productID);

    List<Subcategory> findByProducts_Id(Integer productID, Pageable pageable);
}