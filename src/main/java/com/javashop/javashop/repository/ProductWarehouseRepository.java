package com.javashop.javashop.repository;

import com.javashop.javashop.model.Complaint;
import com.javashop.javashop.model.ProductSupplier;
import com.javashop.javashop.model.ProductWarehouse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductWarehouseRepository extends JpaRepository<ProductWarehouse, Integer> {
    List<ProductWarehouse> findByIdIn(List<Integer> id, Pageable pageable);
    Long countByIdIn(List<Integer> id);
}
