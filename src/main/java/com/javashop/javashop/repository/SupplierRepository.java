package com.javashop.javashop.repository;

import com.javashop.javashop.model.Complaint;
import com.javashop.javashop.model.Supplier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    List<Supplier> findByIdIn(List<Integer> id, Pageable pageable);
    Long countByIdIn(List<Integer> id);
}