package com.lm.pedro.test.receipt.controller;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.lm.pedro.test.receipt.dto.ItemQuantityDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("mock")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReceiptControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    @Before
    public void setup() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    }

    @Test
    public void shouldGenerateReceipt() {
        ItemQuantityDto dtoToSend = ItemQuantityDto.builder()
                .itemId(3L)
                .quantity(2)
                .build();
        HttpEntity<List<ItemQuantityDto>> requestInsert = new HttpEntity<>(Collections.singletonList(dtoToSend), headers);

        ResponseEntity<String> response = restTemplate.exchange("/receipts", HttpMethod.POST, requestInsert, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), notNullValue());
        ReadContext context = JsonPath.parse(response.getBody());
        log.info("Generated receipt JSON response: {}", context.jsonString());
        assertThat(context.read("$['salesTax']"), is(0.00D));
        assertThat(context.read("$['total']"), is(1.70D));
    }

}
