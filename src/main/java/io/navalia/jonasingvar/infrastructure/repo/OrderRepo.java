package io.navalia.jonasingvar.infrastructure.repo;

import io.navalia.jonasingvar.domain.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, UUID> {
}
