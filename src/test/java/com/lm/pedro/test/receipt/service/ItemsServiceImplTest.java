package com.lm.pedro.test.receipt.service;

import com.lm.pedro.test.receipt.model.entities.Item;
import com.lm.pedro.test.receipt.model.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class ItemsServiceImplTest {

    @InjectMocks
    private ItemsServiceImpl itemsService;

    @Mock
    private ItemRepository itemRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getItemsEmpty() {
        when(itemRepository.findByActiveTrue()).thenReturn(Collections.emptyList());
        List<Item> actual = itemsService.getItems();
        assertThat(actual, notNullValue());
        assertThat(actual, empty());
    }

    @Test
    public void getItemsOneValue() {
        Item item = createItem("", 15.99D, true, false, true);
        when(itemRepository.findByActiveTrue()).thenReturn(singletonList(item));
        List<Item> actual = itemsService.getItems();
        assertThat(actual, notNullValue());
        assertThat(actual, hasSize(1));
        verify(itemRepository, times(1)).findByActiveTrue();
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    public void getItemExist() {
        Item item = createItem("", 15.99D, true, false, true);
        long id = 1234L;
        when(itemRepository.findById(id)).thenReturn(Optional.of(item));

        Item actual = itemsService.getItem(id);
        assertThat(actual, notNullValue());

        verify(itemRepository, times(1)).findById(id);
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    public void getItemDoesntExist() {
        long id = 1234L;
        when(itemRepository.findById(id)).thenReturn(Optional.empty());

        Item actual = itemsService.getItem(id);
        assertThat(actual, nullValue());

        verify(itemRepository, times(1)).findById(id);
        verifyNoMoreInteractions(itemRepository);
    }

    private Item createItem(final String name, final double price, final boolean exempt, final boolean imported, final boolean active) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setExempt(exempt);
        item.setImported(imported);
        item.setActive(active);
        return item;
    }
}
