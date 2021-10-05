package com.nail.controller;

import com.nail.domain.request.ClientCreateRequest;
import com.nail.domain.request.ClientUpdateRequest;
import com.nail.domain.response.ClientResponse;
import com.nail.mapper.ClientMapper;
import com.nail.service.ClientService;
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

@Api(value = "Client Controller") // descrevendo essa classe no Swagger
@RestController // é um controlador especial usado para serviços RESTFul.
// simplesmente retorna o objeto e os dados do objeto são gravados diretamente na resposta HTTP como JSON ou XML.
@RequestMapping(value = "clients", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

    // A camada de Controller conversa somente com a camada de Servico
    // E a camada de Serviços que se encarrega de conversar com a persistêcia de dados
    @Autowired // Aqui eu estou fazendo uma Injeção de dependência, para meu Service usar o controller
    private ClientService service;

    private final ClientMapper mapper = Mappers.getMapper(ClientMapper.class);

    @GetMapping
    @ApiOperation("Find All clients")//descrevendo o endpoint
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
    }//@Valid é essencial para acionar a validação de atributos aninhados

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a client by id")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
