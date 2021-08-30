package com.nexti.domain.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientUpdateRequest {
    private String name;
    private String cpf;
    private LocalDate birthDate;
}
