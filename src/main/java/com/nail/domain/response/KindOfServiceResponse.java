package com.nail.domain.response;

import com.nail.domain.model.Client;
import com.nail.domain.model.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class KindOfServiceResponse {

    private Long id;
    private String description;
    private BigDecimal amount;
    private Client client;
    private User user;
}
