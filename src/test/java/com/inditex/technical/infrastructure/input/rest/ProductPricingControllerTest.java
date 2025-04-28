package com.inditex.technical.infrastructure.input.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.inditex.technical.application.usecase.FindProductPricingUseCase;
import com.inditex.technical.domain.exceptions.PriceNotFoundException;
import com.inditex.technical.infrastructure.mapper.ProductPricingMapper;

import jakarta.persistence.EntityNotFoundException;

@WebMvcTest(ProductPricingController.class)
class ProductPricingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FindProductPricingUseCase useCase;

    @MockitoBean
    private ProductPricingMapper mapper;

    @Test
    void testFindPrice_ReturnsCorrectResponse() throws Exception {

        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        mockMvc.perform(get("/api/v1/prices")
                        .param("date", date.toString())
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetProductPrice_NotFound() throws Exception {
        when(useCase.findPrice(any(LocalDateTime.class), any(Long.class), any(Long.class))).thenThrow(new EntityNotFoundException("Price not found"));

        mockMvc.perform(get("/api/v1/prices")
                .param("date", "2020-06-14T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetProductPrice_BadRequest_WhenProductIdIsNotNumber() throws Exception {
        mockMvc.perform(get("/api/v1/prices")
                .param("date", "2020-06-14T10:00:00")
                .param("productId", "not-a-number")
                .param("brandId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetProductPrice_Error500() throws Exception {
        when(useCase.findPrice(any(LocalDateTime.class), any(Long.class), any(Long.class))).thenThrow(new RuntimeException("Internal Server Error"));

        mockMvc.perform(get("/bad")
                .param("date", "2020-06-14T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void whenPriceNotFound_thenReturns404AndErrorResponse() throws Exception {

        when(useCase.findPrice(any(LocalDateTime.class), any(Long.class), any(Long.class))).thenThrow(new PriceNotFoundException("Price not found"));

        mockMvc.perform(get("/api/v1/prices")
            .param("date", "2025-06-14T10:00:00")
            .param("productId", "111")
            .param("brandId", "111"))
            .andExpect(status().isNotFound());
    }
}