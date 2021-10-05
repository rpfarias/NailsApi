package com.nail.mapper;

import com.nail.domain.model.Client;
import com.nail.domain.request.ClientCreateRequest;
import com.nail.domain.request.ClientUpdateRequest;
import com.nail.domain.response.ClientResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/* @Mapper - é utlizado par fazer o mapeamento de entidades.
Para ajudar a fazer a converção de um tipo de Entidade para um objeto de transferênci de dados. */
@Mapper // é utilizado para dizermos que esta interface é um mapper
public interface ClientMapper {

    ClientResponse toResponse(Client client);

    List<ClientResponse> toResponseList(List<Client> clients);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(ClientCreateRequest request, @MappingTarget Client client);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(ClientUpdateRequest request, @MappingTarget Client client);
}
