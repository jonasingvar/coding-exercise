package io.navalia.jonasingvar.application.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void fails_validation_missing_product_list() {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setProducts(null);

        var violations = validator.validate(orderDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v ->
                v.getPropertyPath().toString().equals("products") &&
                        v.getMessage().equals("An order must contain an array of 'products'")
        );
    }

    @Test
    void fails_validation_empty_product_list() {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setProducts(Collections.emptyList());

        var violations = validator.validate(orderDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v ->
                v.getPropertyPath().toString().equals("products") &&
                        v.getMessage().equals("There must be at least one product in your order")
        );
    }
}