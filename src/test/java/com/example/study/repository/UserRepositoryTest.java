package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest extends StudyApplicationTests {

    @Autowired  // DI == singleton
    private UserRepository userRepository;

    @Test
    public void create() {
        User user = new User(); // not singleton
        user.setAccount("testuser03");
        user.setEmail("test03@gmail.com");
        user.setPhoneNumber("923232332");
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("admin");

        User newUser = userRepository.save(user);
        System.out.println(("new User : " + newUser));
    }

    @Test
    public void read() {
        Optional<User> user = userRepository.findById(2L);
        user.ifPresent(selectUser -> {
            System.out.println("user :" + selectUser);
        });
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

    public void delete() {

    }

}