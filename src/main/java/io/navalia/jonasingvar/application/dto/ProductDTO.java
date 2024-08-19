package io.navalia.jonasingvar.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    public static final String MUST_BE_GREAT_THAN_0 = "Must be great than 0";
    public static final String MANDATORY = "is mandatory";

    @NotNull(message = MANDATORY)
    @Size(min = 3, max = 50, message = "'productKey'' must be between 3 and 50 characters")
    private String productKey;
    @NotNull(message = MANDATORY)
    @Min(value = 0, message = MUST_BE_GREAT_THAN_0) // should be moved to an @interface and reused
    private Integer quantity;
    @NotNull(message = MANDATORY)
    @Min(value = 0, message = MUST_BE_GREAT_THAN_0)
    private BigDecimal price;
    @NotNull(message = MANDATORY)
    @Min(value = 0, message = MUST_BE_GREAT_THAN_0)
    private BigDecimal discount;
    @NotNull(message = MANDATORY)
    @Size(min = 3, max = 500, message = "'productKey'' must be between 3 and 500 characters")
    private String name;
}