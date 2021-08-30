package com.nexti.controller;

import com.nexti.domain.request.ClientCreateRequest;
import com.nexti.domain.request.ClientUpdateRequest;
import com.nexti.domain.response.ClientResponse;
import com.nexti.mapper.ClientMapper;
import com.nexti.service.ClientService;
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

@Api(value = "Client Controller")
@RestController
@RequestMapping(value = "clients", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

    @Autowired
    private ClientService service;

    private final ClientMapper mapper = Mappers.getMapper(ClientMapper.class);

    @GetMapping
    @ApiOperation("Find All clients")
    public ResponseEntity<List<ClientResponse>> findAll() {
        return new ResponseEntity<>(mapper.toResponseList(service.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("Find a client by id")
    public ResponseEntity<ClientResponse> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(mapper.toResponse(service.findById(id)), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Create a client")
    public ResponseEntity<ClientResponse> create(@RequestBody @Valid ClientCreateRequest request) {
        return new ResponseEntity<>(mapper.toResponse(service.create(request)), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ApiOperation("Update a client by id")
    public ResponseEntity<ClientResponse> update(@PathVariable("id") Long id, @RequestBody @Valid ClientUpdateRequest request) {
        return new ResponseEntity<>(mapper.toResponse(service.update(id, request)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a client by id")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
