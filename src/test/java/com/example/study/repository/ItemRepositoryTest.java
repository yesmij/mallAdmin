package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

class ItemRepositoryTest extends StudyApplicationTests {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void create() {
        Item item = new Item();
        item.setName("notebook");
        item.setStatus("UNREGISTERED");
        item.setTitle("laptop A100");
        item.setPrice(100000);
        item.setContent("Samsung....");
        item.setBrandName("Samsung");
        item.setCreatedAt(LocalDateTime.now());
        item.setRegisteredAt(LocalDateTime.now());
        item.setCreatedBy("Partner01");
//        item.setPartnerId(1L);

        Item savedItem = itemRepository.save(item);
        System.out.println(("new iTem : " + savedItem));
    }

    @Test
    public void read() {
        Optional<Item>  item = itemRepository.findById(1L);
        Assertions.assertTrue(item.isPresent());
    }

}