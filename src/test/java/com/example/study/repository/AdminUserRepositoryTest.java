package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.AdminUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class AdminUserRepositoryTest extends StudyApplicationTests  {

    @Autowired
    AdminUserRepository adminUserRepository;

    @Test
    public void create() {
        AdminUser adminUser = new AdminUser();
        adminUser.setAccount("Admin01");
        adminUser.setPassword("Admin01");
        adminUser.setStatus("REGISERED");
        adminUser.setRole("SUPER");
        adminUser.setLastLoginAt(LocalDateTime.now());
        adminUser.setCreatedAt((LocalDateTime.now()));
        adminUser.setCreatedBy("Super Admin");

        AdminUser savedAdminUser = adminUserRepository.save(adminUser);
        Assertions.assertNotNull(savedAdminUser);
    }
}
