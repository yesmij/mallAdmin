package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

public class CategoryRepositoryTest extends StudyApplicationTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void create() {
        String type ="computer";
        String title = "PC";
        LocalDateTime createAt = LocalDateTime.now();
        String createdBy = "AdminUser";

        Category category = new Category();
        category.setType(type);
        category.setTitle(title);
        category.setCreatedAt(createAt);
        category.setCreatedBy(createdBy);
        Category savedCategory = categoryRepository.save(category);

        Assertions.assertNotNull(savedCategory);
        Assertions.assertEquals(type, savedCategory.getType());
    }

    @Test
    public void read() {
        Optional<Category> optionalCategory = categoryRepository.findById(1L);
//        Optional<Category> optionalCategory = categoryRepository.findByType("computer");

        optionalCategory.ifPresent( result -> {
            System.out.println(result.getId());
            System.out.println(result.getTitle());
            System.out.println(result.getCreatedAt());
        });
    }
}
