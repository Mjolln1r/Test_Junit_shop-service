package ru.itis.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.shop.models.Discount;

import java.util.List;

/**
 * 09.05.2021
 * shop-service
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public interface DiscountsRepositoryDataJpa extends JpaRepository<Discount, Long> {
    List<Discount> findAllByType(Discount.Type type);
}
