package com.javashop.javashop.repository;

import com.javashop.javashop.model.ComplaintType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintTypeRepository extends JpaRepository<ComplaintType, Integer> {

}