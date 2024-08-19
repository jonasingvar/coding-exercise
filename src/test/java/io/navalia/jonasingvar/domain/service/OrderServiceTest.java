package io.navalia.jonasingvar.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.navalia.jonasingvar.TestData;
import io.navalia.jonasingvar.application.dto.OrderDTO;
import io.navalia.jonasingvar.domain.model.OrderEntity;
import io.navalia.jonasingvar.domain.model.OutboxEventEntity;
import io.navalia.jonasingvar.infrastructure.repo.OrderRepo;
import io.navalia.jonasingvar.web.exception.NotFoundException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

  @Mock
  private OrderRepo orderRepo;

  @Mock
  private EntityManager entityManager;

  @Mock
  private ObjectMapper objectMapper;

  @InjectMocks
  private OrderService orderService;

  private OrderEntity orderEntity;
  private UUID orderId = UUID.randomUUID();

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    orderId = UUID.randomUUID();
    orderEntity = new OrderEntity();
    orderEntity.setId(orderId);

    // Simulate generating a UUID
    doAnswer(invocation -> {
      Object[] args = invocation.getArguments();
      OrderEntity entity = (OrderEntity) args[0];
      entity.setId(orderId);
      return null;
    }).when(entityManager).persist(any(OrderEntity.class));
  }

  @Test
  void finds_order() {
    when(orderRepo.findById(orderId)).thenReturn(Optional.of(orderEntity));
    assertEquals(orderId, orderService.getOrderById(orderId).getId());
  }

  @Test
  void order_not_found() {
    when(orderRepo.findById(orderId)).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> orderService.getOrderById(orderId));
  }

  @Test
  void submits_order() throws JsonProcessingException {
    OrderDTO dto = TestData.getOrderDTO();
    var response = orderService.submit(dto);

    assertThat(response.getTotalGross()).isEqualByComparingTo(new BigDecimal(227));
    assertThat(response.getTotalNet()).isEqualByComparingTo(new BigDecimal(207));

    assertThat(response.getProducts().size()).isEqualTo(2);

    verify(entityManager).persist(argThat(order -> order instanceof OrderEntity && ((OrderEntity) order).getId().equals(orderId)));
    verify(entityManager).persist(argThat(outbox -> outbox instanceof OutboxEventEntity && ((OutboxEventEntity) outbox).getStatus() == OutboxEventEntity.EventStatus.PENDING));
  }
}


