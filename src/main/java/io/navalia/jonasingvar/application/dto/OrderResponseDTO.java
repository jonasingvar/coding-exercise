package io.navalia.jonasingvar.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrderResponseDTO {
    private UUID id;
    private List<ProductDTO> products;
    private BigDecimal totalGross;
    private BigDecimal totalNet;
}
