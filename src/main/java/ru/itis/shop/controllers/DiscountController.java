package ru.itis.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.shop.dto.DiscountsForPricesDto;
import ru.itis.shop.dto.DiscountDto;
import ru.itis.shop.dto.ExceptionDto;
import ru.itis.shop.dto.OrdersPricesDto;
import ru.itis.shop.exceptions.IncorrectPrice;
import ru.itis.shop.services.DiscountsService;

import java.util.List;

/**
 * 09.05.2021
 * shop-service
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@RestController
public class DiscountController {

    @Autowired
    private DiscountsService discountsService;

    @GetMapping("/discounts")
    public ResponseEntity<List<DiscountDto>> getDiscountsByType(@RequestParam("type") String type) {
        return ResponseEntity.ok(discountsService.getDiscountsByType(type));
    }

    @PostMapping(value = "/discounts/apply", params = "type=PERCENTS")
    public ResponseEntity<DiscountsForPricesDto> applyDiscounts(@RequestBody OrdersPricesDto ordersPrices) {
        return ResponseEntity.ok(discountsService.applyDiscounts(ordersPrices));
    }

    @ExceptionHandler(IncorrectPrice.class)
    public ResponseEntity<ExceptionDto> handleIncorrectType(IncorrectPrice exception) {
        return ResponseEntity.badRequest()
                .body(ExceptionDto.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(exception.getMessage())
                        .build());
    }
}
