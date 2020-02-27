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
//        User user = new User(); // not singleton
        String account = "testuser02";
        String password = "testuser02";
        String status = "REGISTERED";
        String email = "test03@gmail.com";
        String phoneNumber = "12345678";

        User user = User.builder()
                .account(account)
                .password(password)
                .status(status)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();

        // Accessor(chain=true)
        // User user1 = new User().setAccount("  ").setEmail("   ").setPassword(" ").....;

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

                orderGroup.getOrderDetailList().forEach(orderDetail -> {

                System.out.println("============ Order Detail ===========");
                System.out.println("status : " + orderDetail.getStatus());
                System.out.println("arrival date : " + orderDetail.getArrivalDate());
                System.out.println("order group : " + orderDetail.getOrderGroup());
                System.out.println("order price : " + orderDetail.getTotalPrice());

                    // Order detail vs Item Relationship 생성 &&&& Item - Partner Relationship
                    System.out.println("============ Item Detail ===========");
                    System.out.println("order item : " + orderDetail.getItem());
                    System.out.println("order item : " + orderDetail.getItem().getName());
                    System.out.println("Partner Name : " + orderDetail.getItem().getPartner().getName());
                    System.out.println("Partner Info : " + orderDetail.getItem().getPartner().getCeoName());
                    // Partner && Category Relationship
                    System.out.println(("Category Name : " + orderDetail.getItem().getPartner().getCategory().getTitle()));
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