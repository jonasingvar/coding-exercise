package io.navalia.jonasingvar.web.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.navalia.jonasingvar.application.dto.OrderDTO;
import io.navalia.jonasingvar.application.dto.OrderResponseDTO;
import io.navalia.jonasingvar.domain.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) throws JsonProcessingException {
        return new ResponseEntity<>(service.submit(orderDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable UUID orderId) {
        return new ResponseEntity<>(service.getOrderById(orderId), HttpStatus.OK);
    }
}

