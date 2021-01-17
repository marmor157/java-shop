package com.javashop.javashop.repository;

import com.javashop.javashop.model.Complaint;
import com.javashop.javashop.model.TaxCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaxCategoryRepository extends JpaRepository<TaxCategory, Integer> {
    List<TaxCategory> findByIdIn(List<Integer> id, Pageable pageable);
    Long countByIdIn(List<Integer> id);
}
