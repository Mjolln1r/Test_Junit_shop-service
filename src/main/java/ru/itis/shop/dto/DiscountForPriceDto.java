package ru.itis.shop.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 09.05.2021
 * shop-service
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@Data
@Builder
public class DiscountForPriceDto {
    private Double percents;
    private Double priceByDiscount;
}
