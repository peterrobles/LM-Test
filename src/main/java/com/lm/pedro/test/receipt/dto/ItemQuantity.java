package com.lm.pedro.test.receipt.dto;

import com.lm.pedro.test.receipt.model.entities.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Min;

@Value
@AllArgsConstructor
@Builder
public class ItemQuantity {

    @Min(1)
    int quantity;

    Item item;
}
