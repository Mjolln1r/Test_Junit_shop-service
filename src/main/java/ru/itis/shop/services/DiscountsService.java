package ru.itis.shop.services;

import org.springframework.stereotype.Service;
import ru.itis.shop.dto.DiscountDto;
import ru.itis.shop.dto.DiscountsForPricesDto;
import ru.itis.shop.dto.OrdersPricesDto;

import java.util.List;

/**
 * 09.05.2021
 * shop-service
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public interface DiscountsService {
    List<DiscountDto> getDiscountsByType(String type);

    DiscountsForPricesDto applyDiscounts(OrdersPricesDto ordersPrices);
}
