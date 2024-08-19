package io.navalia.jonasingvar.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class OrderDTO {

    @Valid
    @NotNull(message = "An order must contain an array of 'products'")
    @Size(min = 1, message = "There must be at least one product in your order")
    private List<ProductDTO> products;
}