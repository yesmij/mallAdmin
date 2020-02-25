package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.OrderDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderDetailRepositoryTest extends StudyApplicationTests {

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Test
    public void create() {
        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setStatus("WAITTING");
        orderDetail.setArrivalDate(LocalDateTime.now().plusDays(3));
        orderDetail.setQuantity(2);
        orderDetail.setTotalPrice(BigDecimal.valueOf(900000));

//        orderDetail.setItemId(1L);
//        orderDetail.setOrderGroupId(1L); // Long --> OrderGroup
        orderDetail.setCreatedAt(LocalDateTime.now());
        orderDetail.setCreatedBy("AdminServer");

        System.out.println("orderDeatial " + orderDetail);
        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);
        System.out.println(savedOrderDetail);
        Assertions.assertNotNull(savedOrderDetail);
    }
}