package com.lm.pedro.test.receipt.service;

import com.lm.pedro.test.receipt.model.entities.Item;
import com.lm.pedro.test.receipt.model.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemsServiceImpl implements ItemsService {

    private final ItemRepository itemRepository;

    @Override
    public List<Item> getItems() {
        return itemRepository.findByActiveTrue();
    }

    @Override
    public Item getItem(final long id) {
        return itemRepository.findById(id).orElse(null);
    }
}
