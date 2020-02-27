package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.Partner;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.PartnerApiRequest;
import com.example.study.model.network.response.PartnerApiResponse;
import com.example.study.repository.CategoryRepository;
import com.example.study.repository.PartnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PartnerApiLogicService implements CrudInterface<PartnerApiRequest, PartnerApiResponse> {

    @Autowired
    private PartnerRepository partnerRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Header<PartnerApiResponse> create(Header<PartnerApiRequest> request) {
        PartnerApiRequest partnerApiRequest = request.getData();
        Partner partner = Partner.builder()
                .partnerNumber(partnerApiRequest.getPartnerNumber())
                .address(partnerApiRequest.getAddress())
                .businessNumber(partnerApiRequest.getBusinessNumber())
                .callCenter(partnerApiRequest.getCallCenter())
                .category(categoryRepository.getOne(partnerApiRequest.getCategoryId()))
                .ceoName(partnerApiRequest.getCeoName())
                .createdAt(partnerApiRequest.getCreatedAt())
                .createdBy(partnerApiRequest.getCreatedBy())
                .status(partnerApiRequest.getStatus())
                .name(partnerApiRequest.getName())
                .registeredAt(partnerApiRequest.getRegisteredAt())
                .build();
        Partner newPartner = partnerRepository.save(partner);
        return response(newPartner);
    }

    @Override
    public Header<PartnerApiResponse> read(Long id) {
        Optional<Partner> optionalPartner = partnerRepository.findById(id);
        return optionalPartner.map(partner -> response(partner))
                .orElseGet(() -> Header.ERROR("read error"));
    }

    private Header<PartnerApiResponse> response(Partner partner) {
        PartnerApiResponse partnerApiResponse = PartnerApiResponse.builder()
                .id(partner.getId())
                .partnerNumber(partner.getPartnerNumber())
                .address(partner.getAddress())
                .businessNumber(partner.getBusinessNumber())
                .callCenter(partner.getCallCenter())
                .ceoName(partner.getCeoName())
                .name(partner.getName())
                .createdAt(partner.getCreatedAt())
                .categoryId(partner.getCategory().getId())
               // .itemList(partner.getItemList())   // todo ItemList
                .status(partner.getStatus())
                .registeredAt(partner.getRegisteredAt())
                .createdBy(partner.getCreatedBy())
                .updatedAt(partner.getUpdatedAt())
                .updatedBy(partner.getUpdatedBy())
                .build();
        return Header.OK(partnerApiResponse);

    }

    @Override
    public Header<PartnerApiResponse> update(Header<PartnerApiRequest> request) {
        PartnerApiRequest partnerApiRequest = request.getData();
        Optional<Partner> partner = partnerRepository.findById(partnerApiRequest.getId());
        log.info("partnerApiRequest : {}", partnerApiRequest.getUpdatedBy());
        return partner.map(updatePartner -> {
            updatePartner.setCallCenter(partnerApiRequest.getCallCenter())
                    .setCreatedBy(partnerApiRequest.getCreatedBy())
                    .setRegisteredAt(partnerApiRequest.getRegisteredAt())
                    .setCeoName(partnerApiRequest.getCeoName())
                    .setPartnerNumber(partnerApiRequest.getPartnerNumber())
                    .setBusinessNumber(partnerApiRequest.getBusinessNumber())
                    .setStatus(partnerApiRequest.getStatus())
                    .setRegisteredAt(partnerApiRequest.getRegisteredAt())
                    .setAddress(partnerApiRequest.getAddress())
                    .setName(partnerApiRequest.getName())
                    .setCategory(categoryRepository.getOne(partnerApiRequest.getCategoryId()))
                    .setCreatedAt(partnerApiRequest.getCreatedAt())
                    .setUpdatedBy(partnerApiRequest.getUpdatedBy())
                    .setUpdatedAt(partnerApiRequest.getUpdatedAt()).builder();    // todo update by, at만 안되는 부분...build() 확인
            Partner updatedPartner = partnerRepository.save(updatePartner);
            return response(updatedPartner);
        }).orElseGet(() -> Header.ERROR("updating failure"));
    }

    @Override
    public Header delete(Long id) {
        return null;
    }
}
