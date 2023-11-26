package com.project.shop.item.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemControllerTest {

    @GetMapping("/get/hello")
    public String GetMappingTest (@RequestParam int id) {

        return "Get Mapping : " + id;
    }

}
