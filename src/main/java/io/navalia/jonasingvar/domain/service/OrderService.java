package io.navalia.jonasingvar.domain.service;

import io.navalia.jonasingvar.application.mapper.ProductMapper;
import io.navalia.jonasingvar.web.order.OrderDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class OrderService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void process(OrderDTO dto) {
        entityManager.persist(ProductMapper.INSTANCE.toOrderEntity(dto));
    }
}
