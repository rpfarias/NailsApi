package com.nexti.mapper;

import com.nexti.domain.model.Client;
import com.nexti.domain.request.ClientCreateRequest;
import com.nexti.domain.request.ClientUpdateRequest;
import com.nexti.domain.response.ClientResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper
public interface ClientMapper {

    ClientResponse toResponse(Client client);

    List<ClientResponse> toResponseList(List<Client> clients);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(ClientCreateRequest request, @MappingTarget Client client);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(ClientUpdateRequest request, @MappingTarget Client client);
}
