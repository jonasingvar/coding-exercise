package io.navalia.jonasingvar.application.mapper;

import io.navalia.jonasingvar.domain.model.ProductEntity;
import io.navalia.jonasingvar.application.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductEntity toEntity(ProductDTO dto);

    ProductDTO toDTO(ProductEntity entity);
}
