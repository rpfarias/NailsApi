package com.nail.service;

import com.nail.domain.model.KindOfService;
import com.nail.domain.request.KindOfServiceRequest;
import com.nail.mapper.KindOfServiceMapper;
import com.nail.repository.KindOfServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
public class KOfService {

    @Autowired
    private KindOfServiceRepository repository;

    private final KindOfServiceMapper mapper = Mappers.getMapper(KindOfServiceMapper.class);

    public List<KindOfService> findAll() {
        log.info("Trying to get all Service.");

        return repository.findAll();
    }

    public KindOfService findById(Long id) {
        log.info(String.format("Trying to get a Service by id { %s }.", id));

        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Service { %s } not found.", id)));
    }

    public KindOfService create(KindOfServiceRequest request) {
        log.info("Trying to create a Service.");

        var kindOfService = new KindOfService();
        mapper.merge(request, kindOfService);
        return repository.saveAndFlush(kindOfService);
    }

    public KindOfService update(Long id, KindOfServiceRequest request) {
        log.info(String.format("Trying to update a service by id { %s }.", id));

        var kindOfService = findById(id);
        mapper.merge(request, kindOfService);
        return repository.saveAndFlush(kindOfService);
    }

    public KindOfService update(KindOfService kindOfService) {
        log.info("Trying to update a Service.");

        return repository.saveAndFlush(kindOfService);
    }

    public void delete(Long id) {
        log.info(String.format("Trying to delete a service by id { %s }.", id));

        var kindOfService = findById(id);
        repository.deleteById(kindOfService.getId());
    }
}
