package com.lm.pedro.test.receipt.controller;

import com.lm.pedro.test.receipt.dto.ItemQuantity;
import com.lm.pedro.test.receipt.dto.ItemQuantityDto;
import com.lm.pedro.test.receipt.dto.ReceiptDto;
import com.lm.pedro.test.receipt.service.ItemsService;
import com.lm.pedro.test.receipt.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/receipts")
@RequiredArgsConstructor
public class ReceiptController {

    private final ItemsService itemsService;
    private final ReceiptService receiptService;

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ReceiptDto generateReceipt(@RequestBody final List<ItemQuantityDto> itemsQuantityDto) {
        List<ItemQuantity> itemsQuantity= itemsQuantityDto.stream()
                .filter(Objects::nonNull)
                .map(i -> ItemQuantity.builder()
                        .quantity(i.getQuantity())
                        .item(itemsService.getItem(i.getItemId()))
                        .build())
                .collect(toList());
        return receiptService.generateReceipt(itemsQuantity);
    }
}
