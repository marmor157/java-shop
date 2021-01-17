package com.javashop.javashop.repository;

import com.javashop.javashop.model.ComplaintType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintTypeRepository extends JpaRepository<ComplaintType, Integer> {

    List<ComplaintType> findByIdIn(List<Integer> id, Pageable pageable);
    Long countByIdIn(List<Integer> id);
}