package com.ohgiraffers.crudtest.model.service;

import com.ohgiraffers.crudtest.model.dao.CategoryRepository;
import com.ohgiraffers.crudtest.model.dao.MenuRepository;
import com.ohgiraffers.crudtest.model.dto.CategoryDTO;
import com.ohgiraffers.crudtest.model.dto.MenuDTO;
import com.ohgiraffers.crudtest.model.entity.Category;
import com.ohgiraffers.crudtest.model.entity.Menu;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository repository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public List<MenuDTO> menuFindAll() {

        List<Menu> menu = repository.findAll();

        return menu.stream().map(menus -> modelMapper.map(menus, MenuDTO.class)).collect(Collectors.toList());
    }

    public MenuDTO menuById(int menuCode) {

        Menu menu = repository.findById(menuCode).orElseThrow(IllegalArgumentException::new);

        return modelMapper.map(menu, MenuDTO.class);
    }

    @Transactional
    public void registMenu(MenuDTO menu) {

        repository.save(modelMapper.map(menu, Menu.class));
    }

    public List<CategoryDTO> categoryAll() {

        List<Category> category = categoryRepository.findAllCategory();

        return category.stream().map(categories -> modelMapper.map(categories, CategoryDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public void menuDelete(int menuCode) {

        modelMapper.map(menuCode, Menu.class);

        repository.deleteById(menuCode);

    }
}
