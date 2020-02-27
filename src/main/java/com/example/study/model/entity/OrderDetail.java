package com.example.study.model.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity //@ToString(exclude = {"user", "item"})  // 연관관계설정은 toString에서 배제
@ToString(exclude = {"orderGroup", "item"})
@EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain=true)
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private LocalDateTime arrivalDate;
    private Integer quantity;
    private BigDecimal totalPrice;
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    //private Long itemId;
    @ManyToOne
    private Item item;

    //private Long orderGroupId;   ---> 이거 대신에 아래 객체로 관리!!!

    // OrderDetail : OrderGroup = N : 1
    @ManyToOne
    private OrderGroup orderGroup;


//    @ManyToOne   // N :1
//    private User user;  //user id --> 객체타입으로 선언해야!!
//
//    @ManyToOne
//    private Item item;
}
