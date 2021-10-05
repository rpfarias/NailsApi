package com.nail.domain.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientResponse {
    private Long id;
    private String name;
    private String phone;
    private LocalDate birthDate;
    private String cpf;
}
