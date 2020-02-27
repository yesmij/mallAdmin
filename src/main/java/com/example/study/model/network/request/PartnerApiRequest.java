package com.example.study.model.network.request;

import com.example.study.model.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartnerApiRequest {
    private Long id;
    private String name;
    private String status;
    private String address;
    private String callCenter;
    private String partnerNumber;
    private String ceoName;
    private String businessNumber;
    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Long categoryId;
    private List<Item> itemList;
}
