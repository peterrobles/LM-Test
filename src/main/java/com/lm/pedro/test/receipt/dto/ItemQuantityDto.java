package com.lm.pedro.test.receipt.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = ItemQuantityDto.ItemQuantityDtoBuilder.class)
public class ItemQuantityDto {
    long itemId;
    int quantity;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ItemQuantityDtoBuilder {
    }
}
