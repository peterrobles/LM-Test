package com.lm.pedro.test.receipt.service;

import com.lm.pedro.test.receipt.dto.ItemQuantity;
import com.lm.pedro.test.receipt.dto.ReceiptDto;
import com.lm.pedro.test.receipt.model.entities.Item;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ReceiptServiceImplTest {

    @InjectMocks
    private ReceiptServiceImpl receiptService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void generateReceipt() {

        List<ItemQuantity> itemsQuantity = new ArrayList<>(2);
        itemsQuantity.add(new ItemQuantity(2, createItem("My product No exempt Imported", 15.67D, false, true)));
        itemsQuantity.add(new ItemQuantity(1, createItem("My product Exempt No Imported", 37.43D, true, false)));
        ReceiptDto actual = receiptService.generateReceipt(itemsQuantity);

        assertThat(actual, notNullValue());
        assertThat(actual.getSalesTax(), is(4.80D));
        assertThat(actual.getTotal(), is(73.57D));
        assertThat(actual.getItems(), notNullValue());
        assertThat(actual.getItems(), hasSize(2));
    }

    @Test
    public void calculateItemSalesTaxNoExemptImported() {
        double expected = 4.80D;
        Item item = createItem("My product No exempt Imported", 15.67D, false, true);
        ItemQuantity itemQuantity = new ItemQuantity(2, item);
        double actual = receiptService.calculateItemSalesTax(itemQuantity);
        assertThat(actual, is(expected));
    }

    @Test
    public void calculateItemSalesTaxNoExemptNoImported() {
        double expected = 3.20D;
        Item item = createItem("My product No exempt No Imported", 15.67D, false, false);
        ItemQuantity itemQuantity = new ItemQuantity(2, item);
        double actual = receiptService.calculateItemSalesTax(itemQuantity);
        assertThat(actual, is(expected));
    }

    @Test
    public void calculateItemSalesTaxNoExemptNoImported2() {
        double expected = 1.90D;
        Item item = createItem("My product No exempt No Imported", 18.99D, false, false);
        ItemQuantity itemQuantity = new ItemQuantity(1, item);
        double actual = receiptService.calculateItemSalesTax(itemQuantity);
        assertThat(actual, is(expected));
    }

    @Test
    public void calculateItemSalesTaxExemptNoImported() {
        double expected = 0.00D;
        Item item = createItem("My product Exempt No imported", 47.5D, true, false);
        ItemQuantity itemQuantity = new ItemQuantity(1, item);
        double actual = receiptService.calculateItemSalesTax(itemQuantity);
        assertThat(actual, is(expected));
    }

    @Test
    public void calculateItemSalesTaxExemptImported() {
        double expected = 2.40D;
        Item item = createItem("My product Exempt Imported", 47.5D, true, true);
        ItemQuantity itemQuantity = new ItemQuantity(1, item);
        double actual = receiptService.calculateItemSalesTax(itemQuantity);
        assertThat(actual, is(expected));
    }

    private Item createItem(final String name, final double price, final boolean exempt, final boolean imported) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setExempt(exempt);
        item.setImported(imported);
        return item;
    }

    @Test
    public void calculatePercentageNotRounding() {
        double amount = 47.50D;
        double percentage = 10.00D;
        double expected = 4.75D;

        double actual = receiptService.calculatePercentageToNearest20th(amount, percentage);
        assertThat(actual, notNullValue());
        assertThat(actual, is(expected));
    }

    @Test
    public void calculatePercentageRoundingClosest() {
        double amount = 47.50D;
        double percentage = 5.00D;
        double expected = 2.40D;

        double actual = receiptService.calculatePercentageToNearest20th(amount, percentage);
        assertThat(actual, notNullValue());
        assertThat(actual, is(expected));
    }

    @Test
    public void calculatePercentageRoundingUp() {
        double amount = 11.25D;
        double percentage = 5.00D;
        double expected = 0.60D;

        double actual = receiptService.calculatePercentageToNearest20th(amount, percentage);
        assertThat(actual, notNullValue());
        assertThat(actual, is(expected));
    }

    @Test
    public void calculatePercentageAmountLessThanOne() {
        double amount = 0.83D;
        double percentage = 5.00D;
        double expected = 0.05D;

        double actual = receiptService.calculatePercentageToNearest20th(amount, percentage);
        assertThat(actual, notNullValue());
        assertThat(actual, is(expected));
    }

    @Test
    public void calculatePercentageAmountLessThanPoint5() {
        double amount = 0.40D;
        double percentage = 5.00D;
        double expected = 0.05D;

        double actual = receiptService.calculatePercentageToNearest20th(amount, percentage);
        assertThat(actual, notNullValue());
        assertThat(actual, is(expected));
    }
}
