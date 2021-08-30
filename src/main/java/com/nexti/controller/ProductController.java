package com.nexti.controller;

import com.nexti.domain.request.ProductRequest;
import com.nexti.domain.response.ProductResponse;
import com.nexti.mapper.ProductMapper;
import com.nexti.service.ProductService;
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

@Api(value = "Product Controller")
@RestController
@RequestMapping(value = "products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    @Autowired
    private ProductService service;

    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @GetMapping
    @ApiOperation("Find All products")
    public ResponseEntity<List<ProductResponse>> findAll() {
        return new ResponseEntity<>(mapper.toResponseList(service.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("Find a product by id")
    public ResponseEntity<ProductResponse> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(mapper.toResponse(service.findById(id)), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Create a product")
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest request) {
        return new ResponseEntity<>(mapper.toResponse(service.create(request)), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ApiOperation("Update a product by id")
    public ResponseEntity<ProductResponse> update(@PathVariable("id") Long id, @RequestBody @Valid ProductRequest request) {
        return new ResponseEntity<>(mapper.toResponse(service.update(id, request)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a product by id")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
