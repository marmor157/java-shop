package com.javashop.javashop.repository;

import com.javashop.javashop.model.Complaint;
import com.javashop.javashop.model.Product;
import com.javashop.javashop.model.Warehouse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
    List<Warehouse> findByIdIn(List<Integer> id, Pageable pageable);
    Long countByIdIn(List<Integer> id);
    Long countByProducts_Id(Integer productID);

    List<Warehouse> findByProducts_Id(Integer productID, Pageable pageable);
}
