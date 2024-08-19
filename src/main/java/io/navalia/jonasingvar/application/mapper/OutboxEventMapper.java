package io.navalia.jonasingvar.application.mapper;

import io.navalia.jonasingvar.application.dto.OrderEventDTO;
import io.navalia.jonasingvar.domain.model.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OutboxEventMapper {
    OutboxEventMapper INSTANCE = Mappers.getMapper(OutboxEventMapper.class);

    @Mapping(target = "products", source = "products")
    OrderEventDTO toEventDto(OrderEntity orderDTO);

}
