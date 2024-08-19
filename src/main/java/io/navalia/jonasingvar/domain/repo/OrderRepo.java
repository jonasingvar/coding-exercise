package io.navalia.jonasingvar.domain.repo;

import io.navalia.jonasingvar.infrastructure.persistence.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, Long> {
}
