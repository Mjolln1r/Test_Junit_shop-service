package ru.itis.shop.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.itis.shop.dto.DiscountForPriceDto;
import ru.itis.shop.dto.DiscountsForPriceDto;
import ru.itis.shop.dto.DiscountsForPricesDto;
import ru.itis.shop.exceptions.IncorrectPrice;

import javax.sql.DataSource;
import java.util.*;

/**
 * 09.05.2021
 * shop-service
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@Service
public class DiscountsRepositoryJdbcImpl implements DiscountsRepositoryJdbc {

    //language=SQL
    private static final String SQL_APPLY_DISCOUNTS = "with all_prices as (select unnest(CAST(? AS double precision[])) as value) " +
            "select price.value, (price.value - price.value / 100.0 * d.value) as by_discount, d.value as discount_value " +
            "from discount d " +
            "         cross join (select * from all_prices) as price " +
            "where d.type = 'PERCENTS' order by price.value;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DiscountsRepositoryJdbcImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final ResultSetExtractor<DiscountsForPricesDto> resultSetExtractor = resultSet -> {
        Map<Double, List<Map<Double, Double>>> byDiscounts = new LinkedHashMap<>();

        while (resultSet.next()) {
            double value = resultSet.getDouble("value");
            double byDiscount = resultSet.getDouble("by_discount");
            double discountValue = resultSet.getDouble("discount_value");

            if (!byDiscounts.containsKey(value)) {
                byDiscounts.put(value, new ArrayList<>());
            }

            byDiscounts.get(value).add(Collections.singletonMap(byDiscount, discountValue));
        }

        List<DiscountsForPriceDto> data = new ArrayList<>();

        for (Map.Entry<Double, List<Map<Double, Double>>> entry : byDiscounts.entrySet()) {

            List<DiscountForPriceDto> discountsForPriceList = new ArrayList<>();
            for (Map<Double, Double> discounts : entry.getValue()) {
                DiscountForPriceDto discountForPrice  = DiscountForPriceDto.builder()
                        .priceByDiscount(discounts.entrySet().iterator().next().getKey())
                        .percents(discounts.entrySet().iterator().next().getValue())
                        .build();
                discountsForPriceList.add(discountForPrice);
            }

            DiscountsForPriceDto discountsForPrice = DiscountsForPriceDto.builder()
                    .price(entry.getKey())
                    .discounts(discountsForPriceList)
                    .build();

            data.add(discountsForPrice);
        }

        return DiscountsForPricesDto.builder()
                .data(data)
                .build();

    };

    @Override
    public DiscountsForPricesDto applyAllDiscounts(List<Double> prices) {
        Double[] asArray = new Double[prices.size()];
        asArray = prices.toArray(asArray);
        Arrays.sort(asArray);
        return jdbcTemplate.query(SQL_APPLY_DISCOUNTS, resultSetExtractor, Arrays.toString(asArray)
                .replaceAll("\\[", "{")
                .replaceAll("]", "}")
        );
    }
}
