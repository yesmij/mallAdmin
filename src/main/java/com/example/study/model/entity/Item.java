package com.example.study.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer price;
    private String content;

    // 1:N
    // Lazy = 지연로팅, Eager = 즉시로딩
    // LAZY : Select * from item where id =?
    // EAGER : item_id = order_detail.item_id, user_id = order_detail.user_id 를 각각 조인후에 (outer join)하면서 검색
    @OneToMany (fetch = FetchType.LAZY, mappedBy = "item")
    private List<OrderDetail> orderDetailList;
}
