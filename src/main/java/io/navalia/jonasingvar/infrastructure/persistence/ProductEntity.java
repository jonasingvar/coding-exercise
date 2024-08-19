package io.navalia.jonasingvar.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "id", updatable = false, nullable = false)
    private String id;
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