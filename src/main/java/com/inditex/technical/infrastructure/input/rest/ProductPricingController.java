package com.inditex.technical.infrastructure.input.rest;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inditex.technical.application.usecase.FindProductPricingUseCase;
import com.inditex.technical.domain.model.ProductPricing;
import com.inditex.technical.infrastructure.dto.ProductPricingResponse;
import com.inditex.technical.infrastructure.mapper.ProductPricingMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(
    path = "/api/v1/prices",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(name = "Prices", description = "API para consultar precios según fecha, producto y marca")
public class ProductPricingController {

    private final FindProductPricingUseCase productPricingService;
    private final ProductPricingMapper productPricingMapper;

    public ProductPricingController(FindProductPricingUseCase productPricingService, ProductPricingMapper productPricingMapper) {
        this.productPricingService = productPricingService;
        this.productPricingMapper = productPricingMapper;
    }

    @GetMapping
    @Operation(
        summary = "Obtener precio aplicable",
        description = "Obtiene el precio a aplicar para un producto y marca en una fecha específica.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Precio encontrado",
                content = @Content(
                    schema = @Schema(implementation = ProductPricingResponse.class),
                    examples = @ExampleObject(
                        value = """
                            {
                              "productId": 35455,
                              "brandId": 1,
                              "priceList": 1,
                              "startDate": "2020-06-14T00:00:00",
                              "endDate": "2020-12-31T23:59:59",
                              "price": 35.50,
                              "currency": "EUR"
                            }
                        """
                    )
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Parámetros inválidos",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                        value = """
                            {
                            "timestamp": "2023-06-14T10:00:00",
                            "status": 400,
                            "error": "Bad Request",
                            "message": "Parámetro inválido: 'date'. Valor recibido: '2020-06-14'",
                            "path": "/api/v1/prices"
                            }
                        """
                    )
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Precio no encontrado",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                        value = """
                            {
                            "timestamp": "2023-06-14T10:00:00",
                            "status": 404,
                            "error": "Not Found",
                            "message": "Precio no encontrado",
                            "path": "/api/v1/prices"
                            }
                        """
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                        value = """
                            {
                              "timestamp": "2023-06-14T10:00:00",
                              "status": 500,
                              "error": "Internal Server Error",
                              "message": "Ocurrió un error inesperado",
                              "path": "/api/v1/prices"
                            }
                        """
                    )
                ))
        })
    public ResponseEntity<ProductPricingResponse> findPrice(
            @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            @Parameter(description = "Fecha en formato ISO 8601", example = "2020-06-14T10:00:00") LocalDateTime date,
            
            @RequestParam(name = "productId") 
            @Parameter(description = "ID del producto", example = "35455") Long productId,

            @RequestParam(name = "brandId") 
            @Parameter(description = "ID de la marca", example = "1") Long brandId
    ) {
        ProductPricing price = this.productPricingService.findPrice(date, productId, brandId);
        ProductPricingResponse productPricingDTO = this.productPricingMapper.fromDomainToDto(price);
        return ResponseEntity.ok(productPricingDTO);
    }}