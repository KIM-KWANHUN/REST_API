package com.ohgiraffers.crudtest.controller;
import com.ohgiraffers.crudtest.model.dto.MenuDTO;
import com.ohgiraffers.crudtest.model.entity.MenuMessage;
import com.ohgiraffers.crudtest.model.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/menu/list")
    public ResponseEntity<MenuMessage> findMenuAll() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<MenuDTO> menuList = menuService.findMenuAll();

        Map<String, Object> response = new HashMap<>();
        response.put("response", menuList);

        System.out.println("response = " + response);
        System.out.println("menuList = " + menuList);

        return ResponseEntity.ok().headers(headers).body(new MenuMessage(200, "전체조회 성공", response));
    }

    @GetMapping("/menu/{menuCode}")
    public ResponseEntity<MenuMessage> findByMenu(@PathVariable int menuCode) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        MenuDTO menu = menuService.findByMenu(menuCode);

        Map<String, Object> res = new HashMap<>();
        res.put("menu", menu);

        System.out.println("res = " + res);
        System.out.println("menu = " + menu);


        return ResponseEntity.ok().headers(headers).body(new MenuMessage(201, "메뉴 상세조회 성공!!", res));
    }

    @PostMapping("/menu/regist")
    public ResponseEntity<?> menuRegist(@RequestBody MenuDTO menuDTO) {

        menuService.menuRegist(menuDTO);

        System.out.println("menuDTO = " + menuDTO);

        return ResponseEntity.created(URI.create("/menu/list")).body(new MenuMessage(201, "메뉴등록성공", null));
    }

    @DeleteMapping("/menu/delete/{menuCode}")
    public ResponseEntity<?> menuDelete(@PathVariable int menuCode) {

        menuService.menuDelete(menuCode);
        
        return ResponseEntity.created(URI.create("/menu/list")).body(new MenuMessage(204, "메뉴 삭제 성공!!", null));
    }

}
