package com.ohgiraffers.crudtest.model.dao;

import com.ohgiraffers.crudtest.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "SELECT * FROM TBL_CATEGORY ORDER BY CATEGORY_CODE", nativeQuery = true)
    List<Category> findAllCategory();
}
