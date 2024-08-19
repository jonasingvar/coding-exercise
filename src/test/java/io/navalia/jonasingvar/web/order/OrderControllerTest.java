package io.navalia.jonasingvar.web.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.navalia.jonasingvar.TestData;
import io.navalia.jonasingvar.application.dto.OrderDTO;
import io.navalia.jonasingvar.application.dto.OrderResponseDTO;
import io.navalia.jonasingvar.application.dto.ProductDTO;
import io.navalia.jonasingvar.domain.service.OrderService;
import io.navalia.jonasingvar.web.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID orderId;

    private OrderDTO orderDTO;
    private OrderResponseDTO orderResponseDTO;

    @BeforeEach
    void setUp() {
        orderDTO = TestData.getOrderDTO();
        orderId = UUID.randomUUID();
        orderResponseDTO = OrderResponseDTO.builder().id(orderId).build();
    }

    @Test
    void submit_order_success() throws Exception {
        // Given
        when(orderService.submit(any(OrderDTO.class))).thenReturn(orderResponseDTO);

        // When & Then
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(orderResponseDTO.getId().toString()));
    }

    @Test
    void submit_order_validation_fails() throws Exception {
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new OrderDTO()))) // Invalid DTO
                .andExpect(status().isBadRequest());
    }

    @Test
    void get_order_success() throws Exception {
        when(orderService.getOrderById(orderId)).thenReturn(orderResponseDTO);

        // When & Then
        mockMvc.perform(get("/api/orders/{orderId}", orderId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderResponseDTO.getId().toString()));
    }

    @Test
    void get_order_not_found() throws Exception {

        var notFoundId = UUID.randomUUID();
        when(orderService.getOrderById(notFoundId)).thenThrow(new NotFoundException("Order not found"));

        mockMvc.perform(get("/api/orders/{orderId}", notFoundId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}