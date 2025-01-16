package com.ohgiraffers.crudtest.model.service;

import com.ohgiraffers.crudtest.model.dao.MenuRepository;
import com.ohgiraffers.crudtest.model.dto.MenuDTO;
import com.ohgiraffers.crudtest.model.entity.MenuEntity;
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

    public List<MenuDTO> findMenuAll() {

        List<MenuEntity> menu = repository.findAll();

        return menu.stream().map(menus -> modelMapper.map(menus, MenuDTO.class)).collect(Collectors.toList());
    }

    public MenuDTO findByMenu(int menuCode) {

        MenuEntity menu = repository.findById(menuCode).orElseThrow(IllegalArgumentException::new);

        return modelMapper.map(menu, MenuDTO.class);
    }

    @Transactional
    public void menuRegist(MenuDTO menuDTO) {

        repository.save(modelMapper.map(menuDTO, MenuEntity.class));
    }

    @Transactional
    public void menuDelete(int menuCode) {

        repository.deleteById(menuCode);

        modelMapper.map(menuCode, MenuEntity.class);
    }
}
