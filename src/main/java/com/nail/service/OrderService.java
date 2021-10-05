package com.nail.service;

import com.nail.domain.model.Order;
import com.nail.domain.model.OrderRow;
import com.nail.domain.model.Product;
import com.nail.domain.request.OrderRequest;
import com.nail.domain.request.ProductOrderRequest;
import com.nail.repository.OrderRepository;
import com.nail.repository.OrderRowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderRowRepository orderRowRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ClientService clientService;

    public List<Order> findAll() {
        log.info("Trying to get all orders.");

        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        log.info(String.format("Trying to get a order by id { %s }.", id));

        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Order { %s } not found.", id)));
    }

    public Order create(OrderRequest request) {
        log.info("Trying to create a order.");

        BigDecimal total = BigDecimal.ZERO;
        List<OrderRow> rows = new ArrayList<>();

        for (ProductOrderRequest product : request.getProducts()) {
            var p = productService.findById(product.getProductId());
            checkStock(p, product.getQuantity());

            p.setQuantity(p.getQuantity() - product.getQuantity());
            productService.update(p);

            rows.add(OrderRow.builder()
                    .productId(p.getId())
                    .productName(p.getName())
                    .productPrice(p.getPrice())
                    .quantity(product.getQuantity())
                    .build());

            // calculating the total
            total = total.add(p.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())));
        }

        // creating and saving  order
        var order = new Order();
        order.setPurchaseDate(LocalDate.now());
        order.setTotal(total);
        order.setRows(rows);
        var savedOrder = orderRepository.saveAndFlush(order);

        // saving rows
        for (OrderRow toSave : rows) {
            toSave.setOrderId(savedOrder.getId());
            orderRowRepository.saveAndFlush(toSave);
        }

        return findById(savedOrder.getId());
    }

    public Order addClient(Long orderId, Long clientId) {
        var client = clientService.findById(clientId);
        var order = findById(orderId);

        if (order.getClientId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is already a customer linked to this order.");

        order.setClientId(client.getId());
        orderRepository.saveAndFlush(order);
        return order;
    }

    public Order removeClient(Long orderId) {
        var order = findById(orderId);
        order.setClientId(null);
        orderRepository.saveAndFlush(order);
        return order;
    }

    public Order addRow(Long orderId, ProductOrderRequest row) {
        var order = findById(orderId);
        var product = productService.findById(row.getProductId());

        checkStock(product, row.getQuantity());

        product.setQuantity(product.getQuantity() - row.getQuantity());
        productService.update(product);

        var orderRow = OrderRow.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productPrice(product.getPrice())
                .quantity(row.getQuantity())
                .orderId(order.getId())
                .build();
        orderRowRepository.saveAndFlush(orderRow);

        order.setTotal(order.getTotal().add(product.getPrice().multiply(BigDecimal.valueOf(row.getQuantity()))));
        orderRepository.saveAndFlush(order);

        return findById(order.getId());
    }

    public Order removeRow(Long orderId, Long rowId) {
        var order = findById(orderId);
        var orderRow = orderRowRepository.findById(rowId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Row { %s } not found.", rowId)));

        checkIfRowExistInOrder(order, orderRow);

        var product = productService.findById(orderRow.getProductId());
        product.setQuantity(product.getQuantity() + orderRow.getQuantity());
        productService.update(product);

        BigDecimal total = BigDecimal.ZERO;
        for (OrderRow row : order.getRows()) {
            if (!Objects.equals(row.getId(), rowId)) {
                total = total.add(row.getProductPrice().multiply(BigDecimal.valueOf(row.getQuantity())));
            }
        }

        order.setTotal(total);
        orderRepository.saveAndFlush(order);

        orderRowRepository.removeProductFromOrderRowById(rowId);
        orderRowRepository.deleteById(rowId);

        return findById(order.getId());
    }

    public void delete(Long id) {
        log.info(String.format("Trying to delete a order by id { %s }.", id));

        var order = findById(id);
        orderRepository.deleteById(order.getId());
    }

    private void checkStock(Product product, Integer quantityRequested) {
        if (quantityRequested > product.getQuantity())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("The quantity of products ordered { %s } is greater than the quantity in stock { %s }.", quantityRequested, product.getQuantity()));
    }

    private void checkIfRowExistInOrder(Order order, OrderRow orderRow) {
        boolean throwErr = true;
        for (OrderRow row : order.getRows()) {
            if (Objects.equals(row.getId(), orderRow.getId()))
                throwErr = false;
        }

        if (throwErr)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("The Order { %s } and the Row { %s } entered are not part of the same object.", order.getId(), orderRow.getId()));
    }
}
