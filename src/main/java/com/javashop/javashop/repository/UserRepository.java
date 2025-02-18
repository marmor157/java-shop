package com.javashop.javashop.repository;

import com.javashop.javashop.model.Complaint;
import com.javashop.javashop.model.Product;
import com.javashop.javashop.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByRoleId(Integer id, Pageable pageable);
    List<User> findByRoleIdAndIdIn(Integer role_id, List<Integer> id, Pageable pageable);
    List<User> findByIdIn(List<Integer> id, Pageable pageable);
    Long countByIdIn(List<Integer> id);
    User findByLogin(String login);
}