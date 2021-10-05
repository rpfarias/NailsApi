package com.nail.mapper;

import com.nail.domain.model.KindOfService;

import com.nail.domain.request.KindOfServiceRequest;
import com.nail.domain.response.KindOfServiceResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper
public interface KindOfServiceMapper {

    KindOfServiceResponse toResponse(KindOfService kindOfService);

    List<KindOfServiceResponse> toResponseList(List<KindOfService> services);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(KindOfServiceRequest request, @MappingTarget KindOfService kindOfService);

}
