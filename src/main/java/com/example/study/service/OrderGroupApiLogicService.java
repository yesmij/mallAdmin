package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.OrderGroup;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.OrderGroupApiRequest;
import com.example.study.model.network.response.OrderGroupApiResponse;
import com.example.study.repository.OrderDetailRepository;
import com.example.study.repository.OrderGroupRespository;
import com.example.study.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class OrderGroupApiLogicService implements CrudInterface<OrderGroupApiRequest, OrderGroupApiResponse> {

    @Autowired
    private OrderGroupRespository orderGroupRespository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Header<OrderGroupApiResponse> create(Header<OrderGroupApiRequest> request) {
        OrderGroupApiRequest body = request.getData();
        OrderGroup orderGroup = OrderGroup.builder()
                .revName(body.getRevName())
                .revAddress(body.getRevAddress())
                .orderType(body.getOrderType())
                .arrivalDate(body.getArrivalDate())
                .status(body.getStatus())
                .orderAt(body.getOrderAt())
                .paymentType(body.getPaymentType())
                .totalPrice(body.getTotalPrice())
                .totalQuantity(body.getTotalQuantity())
                .user(userRepository.getOne(body.getUserId()))
                .build();

        log.info("userRepository.getOne(body.getUserId()) : {} ", userRepository.getOne(body.getUserId()));
        log.info("body.getUserId() : {} ", body.getUserId());

        OrderGroup newOrderGroup = orderGroupRespository.save(orderGroup);
        return responseBody(newOrderGroup);
    }

    @Override
    public Header<OrderGroupApiResponse> read(Long id) {
        Optional<OrderGroup> optionalOrderGroup = orderGroupRespository.findById(id);
        return optionalOrderGroup.map( orderGroup -> responseBody(orderGroup))
                .orElseGet(() -> Header.ERROR("fail reading"));
    }

    private Header<OrderGroupApiResponse> responseBody(OrderGroup orderGroup) {
        OrderGroupApiResponse orderGroupApiResponse = OrderGroupApiResponse.builder()
                .id(orderGroup.getId())
                .revName(orderGroup.getRevName())
                .revAddress(orderGroup.getRevAddress())
                .paymentType(orderGroup.getPaymentType())
                .arrivalDate(orderGroup.getArrivalDate())
                .status(orderGroup.getStatus())
                .orderAt(orderGroup.getOrderAt())
                .totalPrice(orderGroup.getTotalPrice())
                .totalQuantity(orderGroup.getTotalQuantity())
//                .orderDetailList(orderGroup.getOrderDetailList())
                .orderType(orderGroup.getOrderType())
                .userId(orderGroup.getId())
                .createdAt(orderGroup.getCreatedAt())
                .build();

        // TODO: 2020-02-26   orderDetail 보여주기!!
        log.info("orderDetailList : {}", orderGroup.getOrderDetailList());
        return Header.OK(orderGroupApiResponse);
    }

    @Override
    public Header<OrderGroupApiResponse> update(Header<OrderGroupApiRequest> request) {
        OrderGroupApiRequest body = request.getData();
        Optional<OrderGroup> optionalOrderGroup = orderGroupRespository.findById(body.getId());
        return optionalOrderGroup.map( orderGroup -> {
            orderGroup.setRevName(body.getRevName())
                    .setArrivalDate(body.getArrivalDate())
                    .setRevAddress(body.getRevAddress())
                    .setOrderAt(body.getOrderAt())
                    .setTotalQuantity(body.getTotalQuantity())
                    .setTotalPrice(body.getTotalPrice())
                    .setOrderType(body.getOrderType())
                    .setStatus(body.getStatus())
                    .setPaymentType(body.getPaymentType())
                    .setOrderDetailList(body.getOrderDetailList())
                    .builder();
            OrderGroup updateOrderGroup = orderGroupRespository.save(orderGroup);
            return responseBody(updateOrderGroup);
        }).orElseGet(()-> Header.ERROR("Fail Update"));
    }

    @Override
    public Header delete(Long id) {
        Optional<OrderGroup> optionalOrderGroup = orderGroupRespository.findById(id);
        return optionalOrderGroup.map(deleteOrder -> {
            orderGroupRespository.delete(deleteOrder);
            return Header.OK();
        }).orElseGet(()-> Header.ERROR("delete faile"));
    }
}
