package com.javashop.javashop.repository;

import com.javashop.javashop.model.Complaint;
import com.javashop.javashop.model.Product;
import com.javashop.javashop.model.Subcategory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategories_Id(Integer id, Pageable pageable);
    List<Product> findBySubcategories_Id(Integer id, Pageable pageable);
    List<Product> findByTaxCategoryId(Integer id, Pageable pageable);
    List<Product> findByTaxCategoryIdAndIdIn(Integer tax_category_id, List<Integer> id, Pageable pageable);
    List<Product> findByIdIn(List<Integer> id, Pageable pageable);
    Long countByIdIn(List<Integer> id);

    List<Product> findByOrders_Id(Integer orderID, Pageable pageable);

    List<Product> findByOpinions_Id(Integer opinionID, Pageable pageable);

    List<Product> findByVisited_Id(Integer visitedID, Pageable pageable);

    List<Product> findByWishlist_Id(Integer wishlistID, Pageable pageable);

    List<Product> findByWarehouses_Id(Integer warehouseID, Pageable pageable);

    List<Product> findBySuppliers_Id(Integer supplierID, Pageable pageable);

    Long countByOrders_Id(Integer orderID);

    Long countByOpinions_Id(Integer opinionID);

    Long countByVisited_Id(Integer visitedID);

    Long countByWishlist_Id(Integer wishlistID);

    Long countByWarehouses_Id(Integer warehouseID);

    Long countBySuppliers_Id(Integer supplierID);

    @Query("SELECT DISTINCT p FROM Product p JOIN p.categories pc " +
            "WHERE " +
            "p.id != :prod_id AND " +
            "pc.id IN (SELECT c.id from Category c JOIN c.products p WHERE p.id = :prod_id)")
    List<Product> findSimilarByProductId(@Param("prod_id") Integer id, Pageable pageable);
}
