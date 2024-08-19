package io.navalia.jonasingvar.web.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private String id;
    private int quantity;
    private BigDecimal price;
    private BigDecimal discount;
    private String name;
}