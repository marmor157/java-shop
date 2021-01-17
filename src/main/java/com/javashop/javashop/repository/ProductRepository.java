package com.javashop.javashop.repository;

import com.javashop.javashop.model.Complaint;
import com.javashop.javashop.model.Product;
import com.javashop.javashop.model.Subcategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategories_Id(Integer id, Pageable pageable);
    List<Product> findBySubcategories_Id(Integer id, Pageable pageable);
    List<Product> findByTaxCategoryId(Integer id, Pageable pageable);
    List<Product> findByTaxCategoryIdAndIdIn(Integer tax_category_id, List<Integer> id, Pageable pageable);
    List<Product> findByIdIn(List<Integer> id, Pageable pageable);
    Long countByIdIn(List<Integer> id);
}
