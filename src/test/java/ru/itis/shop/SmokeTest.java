package ru.itis.shop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.itis.shop.controllers.DiscountController;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class SmokeTest {

    @Autowired
    private DiscountController discountController;

    @Test
    void contextLoads() {
        assertThat(discountController, is(notNullValue()));
    }

}
