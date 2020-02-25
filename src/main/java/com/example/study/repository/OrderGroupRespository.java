package com.example.study.repository;

import com.example.study.model.entity.OrderGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderGroupRespository extends JpaRepository<OrderGroup, Long> {
}
