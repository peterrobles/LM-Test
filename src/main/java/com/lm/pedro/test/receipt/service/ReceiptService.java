package com.lm.pedro.test.receipt.service;

import com.lm.pedro.test.receipt.dto.ItemQuantity;
import com.lm.pedro.test.receipt.dto.ReceiptDto;

import java.util.List;

public interface ReceiptService {

    ReceiptDto generateReceipt(List<ItemQuantity> itemsQuantity);

    double calculateItemSalesTax(ItemQuantity itemQuantity);

    double calculatePercentageToNearest20th(double amount, double percentage);


}
