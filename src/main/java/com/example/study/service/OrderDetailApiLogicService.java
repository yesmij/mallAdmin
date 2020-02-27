package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.OrderDetail;
import com.example.study.model.entity.OrderGroup;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.OrderDetailApiRequest;
import com.example.study.model.network.response.OrderDetailApiResponse;
import com.example.study.repository.ItemRepository;
import com.example.study.repository.OrderDetailRepository;
import com.example.study.repository.OrderGroupRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class OrderDetailApiLogicService implements CrudInterface<OrderDetailApiRequest, OrderDetailApiResponse> {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderGroupRespository orderGroupRespository;

    @Override
    public Header<OrderDetailApiResponse> create(Header<OrderDetailApiRequest> request) {
        OrderDetailApiRequest orderDetailApiRequest = request.getData();
        // OrderDetail orderDetail = OrderDetail.builder()
        OrderDetail orderDetail = OrderDetail.builder()
                    .arrivalDate(orderDetailApiRequest.getArrivalDate())
                    .status(orderDetailApiRequest.getStatus())
                    .createdAt(orderDetailApiRequest.getCreatedAt())
                    .quantity(orderDetailApiRequest.getQuantity())
                    .totalPrice(orderDetailApiRequest.getTotalPrice())
                    .item(itemRepository.getOne(orderDetailApiRequest.getItemId()))
                    .orderGroup(orderGroupRespository.getOne(orderDetailApiRequest.getOrderGroupId()))
                    .build();
        OrderDetail newOrderDetail = orderDetailRepository.save(orderDetail);
        return responseBody(newOrderDetail);
    }

    @Override
    public Header<OrderDetailApiResponse> read(Long id) {
        Optional<OrderDetail> optionalOrderDetailApiRequest = orderDetailRepository.findById(id);
        log.info("optional : {}", optionalOrderDetailApiRequest);  // todo 릴레이션이 걸린 것들에 대한 Validation 처리 확인!!!
        return optionalOrderDetailApiRequest.map(orderDetail -> responseBody(orderDetail))
                .orElseGet(() -> Header.ERROR("No Read"));
    }

    private Header<OrderDetailApiResponse> responseBody(OrderDetail body) {

        OrderDetailApiResponse orderDetailApiResponse = OrderDetailApiResponse.builder()
                .id(body.getId())
                .status(body.getStatus())
                .arrivalDate(body.getArrivalDate())
                .itemId(body.getItem().getId())     // 확인 할 것!!!
                .quantity(body.getQuantity())
                .totalPrice(body.getTotalPrice())
                .orderGroupId(body.getOrderGroup().getId())  // todo 체크 확인 필요!!!
                .createdAt(body.getCreatedAt())
                .createdBy(body.getCreatedBy())
                .build();
        return Header.OK(orderDetailApiResponse);
    }

    @Override
    public Header<OrderDetailApiResponse> update(Header<OrderDetailApiRequest> request) {
        OrderDetailApiRequest body = request.getData();
    Optional<OrderDetail> optionalOrderDetail = orderDetailRepository.findById(body.getId());
        log.info("find id : {} ", body.getId());
        return optionalOrderDetail.map(orderDetail -> {
        orderDetail.setArrivalDate(body.getArrivalDate())
                .setTotalPrice(body.getTotalPrice())
                .setQuantity(body.getQuantity())
                .setItem(itemRepository.getOne(body.getItemId()))
                .setOrderGroup(orderGroupRespository.getOne(body.getOrderGroupId()))
                .builder();
        OrderDetail updateOrderDetail = orderDetailRepository.save(orderDetail);
        log.info("serve : {} ", updateOrderDetail);
        return responseBody(updateOrderDetail);
    }).orElseGet(() -> Header.ERROR("update fail"));
}

    @Override
    public Header delete(Long id) {
        Optional<OrderDetail> optionalOrderDetail = orderDetailRepository.findById(id);
        return optionalOrderDetail.map(deleteOrder -> {
            orderDetailRepository.delete(deleteOrder);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("delete fail"));
    }
}
