package io.navalia.jonasingvar.application.mapper;


import io.navalia.jonasingvar.infrastructure.persistence.OrderEntity;
import io.navalia.jonasingvar.infrastructure.persistence.ProductEntity;
import io.navalia.jonasingvar.web.order.OrderDTO;
import io.navalia.jonasingvar.web.order.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductEntity toProductEntity(ProductDTO productDTO);

    OrderEntity toOrderEntity(OrderDTO orderDTO);
}