package com.nail.exception;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorDetails {
    private String title;
    private String field;
    private String fieldMessage;
}
/* Personalização da estrutura de resposta a erros
A resposta de erro padrão fornecida pelo Spring Boot contém todos os detalhes que são normalmente necessários.

Aqui eu criei uma estrutura de resposta independente.
E defini uma estrutura de resposta de erro específica. */