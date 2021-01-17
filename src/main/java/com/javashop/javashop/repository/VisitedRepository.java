package com.javashop.javashop.repository;

import com.javashop.javashop.model.Complaint;
import com.javashop.javashop.model.Visited;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitedRepository extends JpaRepository<Visited, Integer> {
    List<Visited> findByIdIn(List<Integer> id, Pageable pageable);
    Long countByIdIn(List<Integer> id);
}