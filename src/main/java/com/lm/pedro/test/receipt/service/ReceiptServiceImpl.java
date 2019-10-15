package com.lm.pedro.test.receipt.service;

import com.lm.pedro.test.receipt.dto.ItemQuantity;
import com.lm.pedro.test.receipt.dto.ReceiptDto;
import com.lm.pedro.test.receipt.dto.ReceiptItemDto;
import com.lm.pedro.test.receipt.model.entities.Item;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static java.lang.Double.sum;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private static final double BASIC_SALES_TAX_PERCENTAGE = 10.00D; // except books, food and medical products
    private static final double IMPORTS_SALES_TAX_PERCENTAGE = 5.00D; // all imported goods
    private static final int DECIMAL_DIGITS = 2;

    public ReceiptDto generateReceipt(final List<ItemQuantity> itemsQuantity) {
        ReceiptDto.ReceiptDtoBuilder receiptBuilder = ReceiptDto.builder();

        double totalSalesTax = 0.00D;
        double total = 0.00D;
        for (ItemQuantity itemQuantity : itemsQuantity) {
            double subtotal = itemQuantity.getItem().getPrice();
            double itemSalesTax = calculateItemSalesTax(itemQuantity);
            int quantity = itemQuantity.getQuantity();

            subtotal = BigDecimal.valueOf(subtotal)
                    .multiply(BigDecimal.valueOf(quantity))
                    .add(BigDecimal.valueOf(itemSalesTax))
                    .setScale(DECIMAL_DIGITS, RoundingMode.HALF_DOWN)
                    .doubleValue();

            total = BigDecimal.valueOf(total)
                    .add(BigDecimal.valueOf(subtotal))
                    .setScale(DECIMAL_DIGITS, RoundingMode.HALF_DOWN)
                    .doubleValue();
            totalSalesTax = BigDecimal.valueOf(totalSalesTax)
                    .add(BigDecimal.valueOf(itemSalesTax))
                    .setScale(DECIMAL_DIGITS, RoundingMode.HALF_DOWN)
                    .doubleValue();

            ReceiptItemDto receiptItemDto = ReceiptItemDto.builder()
                    .name(itemQuantity.getItem().getName())
                    .quantity(quantity)
                    .subtotal(subtotal)
                    .build();
            receiptBuilder.item(receiptItemDto);
        }
        return receiptBuilder.salesTax(totalSalesTax)
                .total(total)
                .build();
    }

    @Override
    public double calculateItemSalesTax(final ItemQuantity itemQuantity) {
        double itemSalesTax = 0.00D;
        Item receiptItem = itemQuantity.getItem();
        double itemPrice = receiptItem.getPrice();
        if (receiptItem.isNotExempt()) {
            itemSalesTax = calculatePercentageToNearest20th(itemPrice, BASIC_SALES_TAX_PERCENTAGE);
        }
        if (receiptItem.isImported()) {
            itemSalesTax = sum(itemSalesTax, calculatePercentageToNearest20th(itemPrice, IMPORTS_SALES_TAX_PERCENTAGE));
        }
        return BigDecimal.valueOf(itemSalesTax)
                .multiply(BigDecimal.valueOf(itemQuantity.getQuantity()))
                .setScale(DECIMAL_DIGITS, RoundingMode.HALF_DOWN)
                .doubleValue();
    }

    @Override
    public double calculatePercentageToNearest20th(final double amount, final double percentage) {
        double preRounding = amount * percentage;
        // Math.round(amount * 20) / 20.0D doesn't always work
        int mod = Math.floorMod((int) preRounding, 5);
        if (mod > 0) {
            preRounding = preRounding + 5 - mod;
        }
        return BigDecimal.valueOf(preRounding)
                .divide(BigDecimal.valueOf(100D), 2, RoundingMode.FLOOR)
                .doubleValue();
    }
}
