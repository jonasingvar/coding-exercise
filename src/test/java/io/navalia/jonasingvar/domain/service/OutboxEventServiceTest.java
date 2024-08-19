package io.navalia.jonasingvar.domain.service;

import io.navalia.jonasingvar.domain.model.OutboxEventEntity;
import io.navalia.jonasingvar.infrastructure.repo.OutboxEventRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OutboxEventServiceTest {

  @Mock
  private OutboxEventRepo outboxEventRepo;

  @InjectMocks
  private OutboxEventService outboxEventService;

  @Test
  void process_2_pending_events() {

    OutboxEventEntity event1 = createPending();
    OutboxEventEntity event2 = createPending();

    List<OutboxEventEntity> pendingEvents = Arrays.asList(event1, event2);

    when(outboxEventRepo.findByStatus(OutboxEventEntity.EventStatus.PENDING)).thenReturn(pendingEvents);

    outboxEventService.processOutboxEvents();

    verify(outboxEventRepo, times(2)).save(any(OutboxEventEntity.class));
    assertThat(event1.getStatus()).isEqualTo(OutboxEventEntity.EventStatus.PROCESSED);
    assertThat(event2.getStatus()).isEqualTo(OutboxEventEntity.EventStatus.PROCESSED);
    verify(outboxEventRepo, times(1)).findByStatus(OutboxEventEntity.EventStatus.PENDING);
  }

  private OutboxEventEntity createPending() {
    var event = new OutboxEventEntity();
    event.setStatus(OutboxEventEntity.EventStatus.PENDING);
    return event;
  }
}