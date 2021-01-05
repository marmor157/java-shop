package com.javashop.javashop.repository;

import com.javashop.javashop.model.ProductSupplier;
import com.javashop.javashop.model.ProductWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductWarehouseRepository extends JpaRepository<ProductWarehouse, Integer> {

}
