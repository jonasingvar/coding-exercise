package io.navalia.jonasingvar.application.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderEventProductDTO {
    private String productKey;
    private String name;
    private int quantity;
    private BigDecimal price;
    private BigDecimal discount;
    private BigDecimal totalPrice;
    private BigDecimal totalDiscount;
}
