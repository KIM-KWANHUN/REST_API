package com.ohgiraffers.crudtest.model.dao;

import com.ohgiraffers.crudtest.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {}
