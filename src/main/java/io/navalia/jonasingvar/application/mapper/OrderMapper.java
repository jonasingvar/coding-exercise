package io.navalia.jonasingvar.application.mapper;


import io.navalia.jonasingvar.application.dto.OrderDTO;
import io.navalia.jonasingvar.application.dto.OrderResponseDTO;
import io.navalia.jonasingvar.domain.model.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = ProductMapper.class)
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "products", source = "products")
    OrderEntity toEntity(OrderDTO orderDTO);

    @Mapping(target = "products", source = "products")
    OrderResponseDTO toDTO(OrderEntity orderEntity);
}