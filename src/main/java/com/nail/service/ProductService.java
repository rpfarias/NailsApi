package com.nail.service;

import com.nail.domain.model.Product;
import com.nail.domain.request.ProductRequest;
import com.nail.mapper.ProductMapper;
import com.nail.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    public List<Product> findAll() {
        log.info("Trying to get all products.");

        return repository.findAll();
    }

    public Product findById(Long id) {
        log.info(String.format("Trying to get a product by id { %s }.", id));

        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Product { %s } not found.", id)));
    }

    public Product create(ProductRequest request) {
        log.info("Trying to create a product.");

        var product = new Product();
        mapper.merge(request, product);
        return repository.saveAndFlush(product);
    }

    public Product update(Long id, ProductRequest request) {
        log.info(String.format("Trying to update a product by id { %s }.", id));

        var product = findById(id);
        mapper.merge(request, product);
        return repository.saveAndFlush(product);
    }

    public Product update(Product product) {
        log.info("Trying to update a product.");

        return repository.saveAndFlush(product);
    }

    public void delete(Long id) {
        log.info(String.format("Trying to delete a product by id { %s }.", id));

        var product = findById(id);
        repository.deleteById(product.getId());
    }
}
