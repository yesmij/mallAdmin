package com.example.study.repository;

import com.example.study.model.entity.OrderDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderDetailRepositoryTest {

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Test
    public void create() {
        OrderDetail orderDetail = new OrderDetail();
//        orderDetail.setItemId(1L);
//        orderDetail.setOrderAt(LocalDateTime.now());
//        orderDetail.setUser(4L);
    }
}