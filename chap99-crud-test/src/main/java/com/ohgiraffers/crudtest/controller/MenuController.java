package com.ohgiraffers.crudtest.controller;

import com.ohgiraffers.crudtest.model.dto.CategoryDTO;
import com.ohgiraffers.crudtest.model.dto.MenuDTO;
import com.ohgiraffers.crudtest.model.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/")
    public String main() {

        return "main/main";
    }

    @GetMapping("menu/list")
    public String menuFindAll(Model model) {

        List<MenuDTO> menuList = menuService.menuFindAll();

        model.addAttribute("menuList", menuList);

        return "menu/list";

    }

    @GetMapping("menu/detail/{menuCode}")
    public String menuById(@PathVariable int menuCode, Model model) {

        MenuDTO menu = menuService.menuById(menuCode);

        model.addAttribute("menu", menu);

        return "menu/detail";

    }

    @GetMapping("menu/regist")
    public void menuRegist(){}

    @PostMapping("menu/regist")
    public String registMenu(@ModelAttribute MenuDTO menu) {

        menuService.registMenu(menu);

        return "redirect:/menu/list";
    }

    @GetMapping(value = "menu/category")
    @ResponseBody
    public List<CategoryDTO> categoryAll() {

        List<CategoryDTO> category = menuService.categoryAll();

        return category;
    }

    @GetMapping("menu/delete/{menuCode}")
    public String menuDelete(@PathVariable int menuCode) {

        menuService.menuDelete(menuCode);

        return "redirect:/menu/list";
    }

}