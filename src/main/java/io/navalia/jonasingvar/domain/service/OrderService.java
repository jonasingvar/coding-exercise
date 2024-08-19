package io.navalia.jonasingvar.domain.service;

import io.navalia.jonasingvar.application.mapper.OrderMapper;
import io.navalia.jonasingvar.domain.model.OutboxEventEntity;
import io.navalia.jonasingvar.application.dto.OrderDTO;
import io.navalia.jonasingvar.application.dto.OrderResponseDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@AllArgsConstructor
@Service
@Validated
public class OrderService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public OrderResponseDTO process(OrderDTO dto) {
        var entity = OrderMapper.INSTANCE.toEntity(dto);
        entityManager.persist(entity);

        var outboxEntity = new OutboxEventEntity();
        outboxEntity.setJson("AAA");
        outboxEntity.setStatus(OutboxEventEntity.EventStatus.PENDING);
        entityManager.persist(outboxEntity);

        return new OrderResponseDTO(entity.getId(), entity.getTotalGross(), entity.getTotalNet());

    }
}
