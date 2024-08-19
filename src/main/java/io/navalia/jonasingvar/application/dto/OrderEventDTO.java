package io.navalia.jonasingvar.application.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderEventDTO {
    private UUID id;
    private List<OrderEventProductDTO> products;
}