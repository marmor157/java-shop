package com.javashop.javashop.repository;


import com.javashop.javashop.model.Complaint;
import com.javashop.javashop.model.ComplaintType;
import com.javashop.javashop.model.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {
    @Query("SELECT c FROM Complaint c WHERE (:complaint_type_id is null or c.complaintType.id = :complaint_type_id) and (:order_id is null"
            + " or c.order.id = :order_id) and (:product_id is null or c.product.id = :product_id) and (:user_id is null or c.user.id = :user_id) " +
            "and (COALESCE(:c_id,NULL) IS NULL or c.id IN (:c_id))")
    List<Complaint> findByComplaintTypeIdAndOrderIdAndProductIdAndUserIdAndIdIn(@Param("complaint_type_id") Integer complaint_type_id,
                                                                           @Param("order_id") Integer order_id,
                                                                           @Param("product_id") Integer product_id,
                                                                            @Param("user_id") Integer user_id,
                                                                           @Param("c_id") List<Integer> id,
                                                                           Pageable pageable);
    List<Complaint> findByIdIn(List<Integer> id, Pageable pageable);
    Long countByIdIn(List<Integer> id);
}