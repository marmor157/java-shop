package com.javashop.javashop.repository;

import com.javashop.javashop.model.Complaint;
import com.javashop.javashop.model.DeliveryAddress;
import com.javashop.javashop.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Integer> {
    List<DeliveryAddress> findByUserId(Integer id, Pageable pageable);
    List<DeliveryAddress> findByUserIdAndIdIn(Integer user_id, List<Integer> id, Pageable pageable);
    List<DeliveryAddress> findByIdIn(List<Integer> id, Pageable pageable);
    Long countByIdIn(List<Integer> id);
}