package com.lm.pedro.test.receipt.service;

import com.lm.pedro.test.receipt.model.entities.Item;

import java.util.List;

public interface ItemsService {

    List<Item> getItems();

    Item getItem(long id);

    /// Methods for CRUD and filter items
}
