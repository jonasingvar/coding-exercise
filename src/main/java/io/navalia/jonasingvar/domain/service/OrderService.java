package io.navalia.jonasingvar.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.navalia.jonasingvar.application.dto.OrderDTO;
import io.navalia.jonasingvar.application.dto.OrderResponseDTO;
import io.navalia.jonasingvar.application.mapper.OrderMapper;
import io.navalia.jonasingvar.application.mapper.OutboxEventMapper;
import io.navalia.jonasingvar.domain.model.OutboxEventEntity;
import io.navalia.jonasingvar.infrastructure.repo.OrderRepo;
import io.navalia.jonasingvar.web.exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;


@AllArgsConstructor
@Service
@Validated
public class OrderService {
    @PersistenceContext
    private EntityManager entityManager;

    private OrderRepo orderRepo;

    private final ObjectMapper objectMapper;

    @Transactional
    public OrderResponseDTO submit(OrderDTO dto) throws JsonProcessingException {
        var entity = OrderMapper.INSTANCE.toEntity(dto);
        entityManager.persist(entity);

        var orderEventDTO = OutboxEventMapper.INSTANCE.toEventDto(entity);

        var outboxEntity = new OutboxEventEntity();
        outboxEntity.setJson(objectMapper.writeValueAsString(orderEventDTO));
        outboxEntity.setStatus(OutboxEventEntity.EventStatus.PENDING);
        entityManager.persist(outboxEntity);

        return OrderMapper.INSTANCE.toDTO(entity);

    }

    public OrderResponseDTO getOrderById(UUID orderId) {
        var orderEntity = orderRepo.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found with id: " + orderId));
        return OrderMapper.INSTANCE.toDTO(orderEntity);
    }
}
