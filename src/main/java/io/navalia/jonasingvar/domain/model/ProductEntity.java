package io.navalia.jonasingvar.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "product_key", updatable = false, nullable = false)
    private String productKey;

    @Column(nullable = false, length = 500)
    @Getter
    @Setter
    private String name;

    @Column(nullable = false)
    @Getter
    @Setter
    private int quantity;

    // Defaults to 2 decimals
    @Column(nullable = false, precision = 12, scale = 4)
    @Getter
    @Setter
    private BigDecimal price;

    // Defaults to 2 decimals
    @Column(nullable = false, precision = 12, scale = 4)
    @Getter
    @Setter
    private BigDecimal discount;

    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal getTotalDiscount() {
        return discount.multiply(BigDecimal.valueOf(quantity));
    }
}