package com.nail.controller;

import com.nail.domain.request.KindOfServiceRequest;
import com.nail.domain.response.KindOfServiceResponse;
import com.nail.mapper.KindOfServiceMapper;
import com.nail.service.KOfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Service Controller")// descrevendo essa classe no Swagger
@RestController
@RequestMapping(value = "service", produces = MediaType.APPLICATION_JSON_VALUE)
public class KindOfServiceController {

    @Autowired
    private KOfService kOfService;

    private final KindOfServiceMapper mapper = Mappers.getMapper(KindOfServiceMapper.class);

    @GetMapping
    @ApiOperation("Find All services")//descrevendo o endpoint
    public ResponseEntity<List<KindOfServiceResponse>> findAll() {
        return new ResponseEntity<>(mapper.toResponseList(kOfService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("Find a product by id")
    public ResponseEntity<KindOfServiceResponse> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(mapper.toResponse(kOfService.findById(id)), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Create a product")
    public ResponseEntity<KindOfServiceResponse> create(@RequestBody @Valid KindOfServiceRequest request) {
        return new ResponseEntity<>(mapper.toResponse(kOfService.create(request)), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ApiOperation("Update a product by id")
    public ResponseEntity<KindOfServiceResponse> update(@PathVariable("id") Long id, @RequestBody @Valid KindOfServiceRequest request) {
        return new ResponseEntity<>(mapper.toResponse(kOfService.update(id, request)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a product by id")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        kOfService.delete(id);
        return ResponseEntity.ok().build();
    }
}
