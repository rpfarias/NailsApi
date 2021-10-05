package com.nail.controller;

import com.nail.domain.request.OrderRequest;
import com.nail.domain.request.ProductOrderRequest;
import com.nail.domain.response.OrderResponse;
import com.nail.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.nail.mapper.OrderMapper.toResponse;
import static com.nail.mapper.OrderMapper.toResponseList;

@Api(value = "Order Controller")// descrevendo essa classe no Swagger
@RestController
@RequestMapping(value = "orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping
    @ApiOperation("Find All orders")//descrevendo o endpoint
    public ResponseEntity<List<OrderResponse>> findAll() {
        return new ResponseEntity<>(toResponseList(service.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("Find an order by id")
    public ResponseEntity<OrderResponse> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(toResponse(service.findById(id)), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Create an order")
    public ResponseEntity<OrderResponse> create(@RequestBody @Valid OrderRequest request) {
        return new ResponseEntity<>(toResponse(service.create(request)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete an order by id")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{orderId}/add-client/{clientId}")
    @ApiOperation("Add client to an order")
    public ResponseEntity<OrderResponse> addClient(@PathVariable("orderId") Long orderId, @PathVariable("clientId") Long clientId) {
        return new ResponseEntity<>(toResponse(service.addClient(orderId, clientId)), HttpStatus.OK);
    }

    @PutMapping("/{orderId}/remove-client")
    @ApiOperation("Remove client from an order")
    public ResponseEntity<OrderResponse> removeClient(@PathVariable("orderId") Long orderId) {
        return new ResponseEntity<>(toResponse(service.removeClient(orderId)), HttpStatus.OK);
    }

    @PostMapping("/{orderId}/add-row")
    @ApiOperation("Add row to an order")
    public ResponseEntity<OrderResponse> addRow(@PathVariable("orderId") Long orderId, @RequestBody @Valid ProductOrderRequest request) {
        return new ResponseEntity<>(toResponse(service.addRow(orderId, request)), HttpStatus.OK);
    }

    @PostMapping("/{orderId}/remove-row/{rowId}")
    @ApiOperation("Remove row from an order")
    public ResponseEntity<OrderResponse> removeRow(@PathVariable("orderId") Long orderId, @PathVariable("rowId") Long rowId) {
        return new ResponseEntity<>(toResponse(service.removeRow(orderId, rowId)), HttpStatus.OK);
    }
}
