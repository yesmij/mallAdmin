package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

class UserRepositoryTest extends StudyApplicationTests {

    @Autowired  // DI == singleton
    private UserRepository userRepository;

    @Test
    public void create() {
        User user = new User(); // not singleton
        user.setAccount("testuser01");
        user.setPassword("testuser01");
        user.setStatus("REGISTERED");
        user.setEmail("test03@gmail.com");
        user.setPhoneNumber("923232332");
        user.setRegisteredAt(LocalDateTime.now());
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("adminUser");

        User newUser = userRepository.save(user);
        Assertions.assertEquals(newUser.getStatus(), "REGISTERED");
        System.out.println(("new User : " + newUser));
    }

    @Test
    @Transactional
    public void read() {
//        Optional<User> user = userRepository.findById(4L);
//        user.ifPresent(selectUser -> {
////            System.out.println("user :" + selectUser);
//            selectUser.getOrderDetailList().stream().forEach(detail -> {
//                System.out.println(detail.getItem());
//            });
//        });

        User user = userRepository.findFirstByPhoneNumberOrderByIdAsc("923232332");
        Assertions.assertNotNull(user);
        user.getOrderGroupList().stream().forEach(orderGroup -> {
            System.out.println("============ Order Group ===========");
            System.out.println("received Name : " + orderGroup.getRevName());
            System.out.println("received Add : " + orderGroup.getRevAddress());
            System.out.println("received Price : " + orderGroup.getTotalPrice());
            System.out.println("received Quantiry : " + orderGroup.getTotalQuantity());

                orderGroup.getOrderDetailList().forEach(orderGroupDetail -> {

                System.out.println("============ Order Detail ===========");
                System.out.println("status : " + orderGroupDetail.getStatus());
                System.out.println("arrival date : " + orderGroupDetail.getArrivalDate());
                System.out.println("order group : " + orderGroupDetail.getOrderGroup());
                System.out.println("order price : " + orderGroupDetail.getTotalPrice());

                });
        });

    }

    @Test
    @Transactional
    public void search() {
//        Optional<User> user = userRepository.findByAccount("testuser03");
//
//        user.ifPresent( selectUser -> {
//            selectUser.getOrderDetailList().stream().forEach( detail -> {
//                Item item = detail.getItem();
//                System.out.println(item);
//            });
//        });
    }

    @Test
    public void update() {
        Optional<User> user = userRepository.findById(1L);
        user.ifPresent(selectUser -> {
            selectUser.setPhoneNumber("3456322");
            selectUser.setUpdatedAt(LocalDateTime.now());
            selectUser.setUpdatedBy("update method()");

            userRepository.save(selectUser);
        });
    }

    @Test
    @Transactional
    public void delete() {
        Optional<User> user = userRepository.findById(3L);

//        Assert
//        Assert.assertTrue(user.isPresent());
        user.ifPresent(selectUser -> {
            userRepository.delete(selectUser);
        });

        Optional<User> deleteUser = userRepository.findById(2L);
        if (deleteUser.isPresent()) {
            System.out.println(" 데이터 존재" + deleteUser.get());
        } else {
            System.out.println(" 데이터 삭제");
        }
    }

}