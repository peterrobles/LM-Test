package com.lm.pedro.test.receipt.dto;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder
public class ReceiptItemDto implements Serializable {
    private static final long serialVersionUID = 6840534117597597020L;

    int quantity;

    String name;

    double subtotal;
}
