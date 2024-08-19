package io.navalia.jonasingvar.application.dto;

import io.navalia.jonasingvar.TestData;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validation_success() {
        assertThat(validator.validate(TestData.getOrderDTO().getProducts().get(0)).size()).isEqualTo(0);
    }

    @Test
    void validation_fails() {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductKey("K"); // Invalid: too short
        productDTO.setQuantity(-1);    // Invalid: less than 0
        productDTO.setPrice(new BigDecimal("-1"));    // Invalid: less than 0
        productDTO.setDiscount(new BigDecimal("-5")); // Invalid: less than 0
        productDTO.setName("Na");      // Invalid: too short

        // Validate the ProductDTO
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);

        // Assert that all violations are caught
        assertThat(violations).hasSize(5);

        assertThat(violations).anyMatch(v ->
                v.getPropertyPath().toString().equals("productKey") &&
                        v.getMessage().contains("must be between 3 and 50 characters")
        );

        assertThat(violations).anyMatch(v ->
                v.getPropertyPath().toString().equals("quantity") &&
                        v.getMessage().contains("Must be great than 0")
        );

        assertThat(violations).anyMatch(v ->
                v.getPropertyPath().toString().equals("price") &&
                        v.getMessage().contains("Must be great than 0")
        );

        assertThat(violations).anyMatch(v ->
                v.getPropertyPath().toString().equals("discount") &&
                        v.getMessage().contains("Must be great than 0")
        );

        assertThat(violations).anyMatch(v ->
                v.getPropertyPath().toString().equals("name") &&
                        v.getMessage().contains("must be between 3 and 500 characters")
        );
    }
}
