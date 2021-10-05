package com.nail.domain.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientUpdateRequest {
    private String name;
    private String phone;
    private String cpf;
    private LocalDate birthDate;
}
