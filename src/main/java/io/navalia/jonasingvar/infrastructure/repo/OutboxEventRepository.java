package io.navalia.jonasingvar.infrastructure.repo;

import io.navalia.jonasingvar.domain.model.OutboxEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEventEntity, UUID> {
    List<OutboxEventEntity> findByStatus(OutboxEventEntity.EventStatus status);
}