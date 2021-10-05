package com.nail.mapper;

import com.nail.domain.model.Product;
import com.nail.domain.request.ProductRequest;
import com.nail.domain.response.ProductResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/* @Mapper - é utlizado par fazer o mapeamento de entidades.
Para ajudar a fazer a converção de um tipo de Entidade para um objeto de transferênci de dados. */
@Mapper
public interface ProductMapper {

    ProductResponse toResponse(Product product);

    List<ProductResponse> toResponseList(List<Product> products);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(ProductRequest request, @MappingTarget Product product);
}
