package io.navalia.jonasingvar.domain.service;

import io.navalia.jonasingvar.domain.model.OutboxEventEntity;
import io.navalia.jonasingvar.infrastructure.repo.OutboxEventRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class OutboxEventService {

  private static final Logger logger = LoggerFactory.getLogger(OutboxEventService.class);

  private OutboxEventRepository outboxEventRepository;

  @Scheduled(fixedRate = 3000) // Runs every 3 seconds
  @Transactional
  public void processOutboxEvents() {
    List<OutboxEventEntity> pendingEvents = outboxEventRepository.findByStatus(OutboxEventEntity.EventStatus.PENDING);

    for (OutboxEventEntity event : pendingEvents) {
      try {

        // Flip status in database
        event.setStatus(OutboxEventEntity.EventStatus.PROCESSED);
        outboxEventRepository.save(event);

        logger.info("Event sent {}", event);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
