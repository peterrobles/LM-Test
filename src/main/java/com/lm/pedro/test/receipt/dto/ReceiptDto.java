package com.lm.pedro.test.receipt.dto;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
@Builder
public class ReceiptDto implements Serializable {
    private static final long serialVersionUID = 8164999935010719382L;

    @Singular
    List<ReceiptItemDto> items;

    double salesTax;

    double total;
}
