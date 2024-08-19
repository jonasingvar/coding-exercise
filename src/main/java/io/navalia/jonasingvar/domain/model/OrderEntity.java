package io.navalia.jonasingvar.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

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

  private BigDecimal calculateTotal(Function<ProductEntity, BigDecimal> mapper) {
    return products.stream().map(mapper).reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public BigDecimal getTotalGross() {
    return calculateTotal(ProductEntity::getTotalPrice);
  }

  public BigDecimal getTotalNet() {
    return calculateTotal(p -> p.getTotalPrice().subtract(p.getTotalDiscount()));
  }
}
