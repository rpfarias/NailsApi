package com.nexti.domain.request;

import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ClientCreateRequest {

    @NotNull
    private String name;

    @NotNull
    @CPF
    private String cpf;

    private LocalDate birthDate;
}
