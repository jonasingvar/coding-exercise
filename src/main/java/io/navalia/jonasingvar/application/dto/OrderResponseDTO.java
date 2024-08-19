package io.navalia.jonasingvar.application.dto;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
public class OrderResponseDTO {

    public UUID id;

    public BigDecimal totalGross;
    public BigDecimal totalNet;
}
