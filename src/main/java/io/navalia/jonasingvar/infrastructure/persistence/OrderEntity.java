package io.navalia.jonasingvar.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
public class OrderEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    @Getter
    @Setter
    private List<ProductEntity> products = new ArrayList<>();

    public BigDecimal getTotalGross() {
        return products.stream()
                .map(ProductEntity::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalNet() {
        return products.stream()
                .map(p -> p.getTotalPrice().min(p.getTotalDiscount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
