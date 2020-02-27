package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.Item;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.ItemApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.repository.ItemRepository;
import com.example.study.repository.PartnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class ItemApiLogicService implements CrudInterface<ItemApiRequest, ItemApiResponse> {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private PartnerRepository partnerRepository;

    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> request) {
        ItemApiRequest itemApiRequest = request.getData();

        Item item = Item.builder()
                .status(itemApiRequest.getStatus())
                .name(itemApiRequest.getName())
                .title(itemApiRequest.getTitle())
                .content(itemApiRequest.getContent())
                .price(itemApiRequest.getPrice())
                .brandName(itemApiRequest.getBrandName())
                .registeredAt(LocalDateTime.now())
                .partner(partnerRepository.getOne(itemApiRequest.getPartnerId()))   // getOne ?
                .build();
        log.info("|| itemApiRequest.getPartnerId() : {}", itemApiRequest.getPartnerId());
        log.info("|| partnerRepository.getOne(itemApiRequest.getPartnerId()): {}", partnerRepository.getOne(itemApiRequest.getPartnerId()));
        Item newItem = itemRepository.save(item);
        return response(newItem);
    }

    @Override
    public Header<ItemApiResponse> read(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        return optionalItem.map(item -> response(item) )
                .orElseGet( () -> Header.ERROR("No ReadItem"));

    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> request) {
        ItemApiRequest itemApiRequest = request.getData();
        Optional<Item> optionalItem = itemRepository.findById(itemApiRequest.getId());
        return optionalItem.map(entityItem -> {
            entityItem.setName(itemApiRequest.getName())
                .setBrandName(itemApiRequest.getBrandName())
                .setTitle(itemApiRequest.getTitle())
                .setContent(itemApiRequest.getContent())
                .setStatus(itemApiRequest.getStatus())
                .setPrice(itemApiRequest.getPrice())
                .setRegisteredAt(itemApiRequest.getRegisteredAt())
                .setUnregisteredAt(itemApiRequest.getUnregisteredAt());
                return entityItem;
        }).map( item ->   itemRepository.save(item))
        .map( updateItem -> response(updateItem))
        .orElseGet( () -> Header.ERROR("Update Failure"));
    }

    @Override
    public Header delete(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        return optionalItem.map( item -> {
            itemRepository.delete(item);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("failed delete"));
    }

    private Header<ItemApiResponse> response(Item item){
        ItemApiResponse body = ItemApiResponse.builder()
                .id(item.getId())
                .status(item.getStatus())
                .name(item.getName())
                .title(item.getTitle())
                .content(item.getContent())
                .price(item.getPrice())
                .brandName(item.getBrandName())
                .registeredAt((item.getRegisteredAt()))
                .unregisterdAt(item.getUnregisteredAt())
                .partnerId(item.getPartner().getId())
                .build();
        return Header.OK(body);
    }
}
