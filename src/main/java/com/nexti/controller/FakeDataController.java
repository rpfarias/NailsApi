package com.nexti.controller;

import com.github.javafaker.Faker;
import com.nexti.domain.model.Client;
import com.nexti.domain.model.Product;
import com.nexti.domain.response.ClientResponse;
import com.nexti.domain.response.ProductResponse;
import com.nexti.mapper.ClientMapper;
import com.nexti.mapper.ProductMapper;
import com.nexti.repository.ClientRepository;
import com.nexti.repository.ProductRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.nexti.util.Generate.cpf;

@Api(value = "Fake Data Controller")
@RestController
@RequestMapping(value = "fake", produces = MediaType.APPLICATION_JSON_VALUE)
public class FakeDataController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    private final ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);
    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @PostMapping("/clients")
    @ApiOperation("Create 10 fake clients")
    public ResponseEntity<List<ClientResponse>> createClients() {

        List<ClientResponse> response = new ArrayList<>();

        var faker = new Faker();

        for (int i = 0; i < 10; i++) {
            Client client = new Client();
            client.setName(faker.name().fullName());
            client.setCpf(cpf(false).replaceAll("[^0-9]*", ""));
            client.setBirthDate(LocalDate.now());
            response.add(clientMapper.toResponse(clientRepository.saveAndFlush(client)));
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/products")
    @ApiOperation("Create 10 fake products")
    public ResponseEntity<List<ProductResponse>> createProducts() {

        List<ProductResponse> response = new ArrayList<>();

        var faker = new Faker();

        for (int i = 0; i < 10; i++) {
            var fruit = faker.food().fruit();

            Product product = new Product();
            product.setName(faker.food().fruit());
            product.setDescription(String.format("The best %s in the world!", fruit));
            product.setQuantity(new Random().nextInt(100));
            product.setPrice(new BigDecimal(BigInteger.valueOf(new Random().nextInt(10000)), 2));
            response.add(productMapper.toResponse(productRepository.saveAndFlush(product)));
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
