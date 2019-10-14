package com.lm.pedro.test.receipt.controller;

import com.lm.pedro.test.receipt.model.entities.Item;
import com.lm.pedro.test.receipt.service.ItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemsController {

    private final ItemsService itemsService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Item> loadItems() {
        return itemsService.getItems();
    }
}
