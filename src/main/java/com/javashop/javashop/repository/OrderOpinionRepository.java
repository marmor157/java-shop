package com.javashop.javashop.repository;

import com.javashop.javashop.model.Opinion;
import com.javashop.javashop.model.Order;
import com.javashop.javashop.model.OrderOpinion;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderOpinionRepository extends JpaRepository<OrderOpinion, Integer> {
    List<OrderOpinion> findByIdIn(List<Integer> id, Pageable pageable);
    Long countByIdIn(List<Integer> id);

    List<OrderOpinion> findByUserId(Integer userID, Pageable pageable);

    List<OrderOpinion> findByOrderId(Integer orderID, Pageable pageable);
}
