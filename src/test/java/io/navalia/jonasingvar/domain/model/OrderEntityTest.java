package io.navalia.jonasingvar.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderEntityTest {

    private OrderEntity orderEntity;

    @BeforeEach
    void setUp() {
        orderEntity = new OrderEntity();
        orderEntity.setId(UUID.randomUUID());

        var product1 = new ProductEntity();
        product1.setPrice(new BigDecimal(100));
        product1.setDiscount(new BigDecimal(10));
        product1.setQuantity(2);

        var product2 = new ProductEntity();
        product2.setPrice(new BigDecimal(50));
        product2.setDiscount(new BigDecimal(5));
        product1.setQuantity(3);

        orderEntity.setProducts(Arrays.asList(product1, product2));
    }

    @Test
    void calculates_gross() {
        assertThat(orderEntity.getTotalGross()).isEqualTo(new BigDecimal(300));
    }

    @Test
    void calculates_net() {
        // after discount
        assertThat(orderEntity.getTotalNet()).isEqualTo(new BigDecimal(270));
    }
}