package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.OrderGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderGroupRepositoryTest extends StudyApplicationTests {

    @Autowired
    private OrderGroupRespository orderGroupRespository;

    @Test
    public void create() {
        OrderGroup orderGroup = new OrderGroup();
        orderGroup.setStatus("COMPLETE");
        orderGroup.setOrderType("ALL");
        orderGroup.setRevAddress("Seoul GangnumGu");
        orderGroup.setRevName("Cho jun");
        orderGroup.setTotalPrice(BigDecimal.valueOf(900000));
        orderGroup.setTotalQuantity(2);
        orderGroup.setOrderAt(LocalDateTime.now().minusDays(3));
        orderGroup.setArrivalDate(LocalDateTime.now());
        orderGroup.setCreatedAt(LocalDateTime.now());
        orderGroup.setCreatedBy("Admin Server");
        // orderGroup.setUserId(1L);  // -> Relation 설정 후에 User Type

        OrderGroup savedOrderGroup = orderGroupRespository.save(orderGroup);
        Assertions.assertNotNull(savedOrderGroup);
    }
}
