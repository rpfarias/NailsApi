package com.nail.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

//No packge exception eu estou tratando os erros de uma forma melhor, para ficar mais bonito.

/* @ControllerAdvice nos permite consolidar nossos múltiplos @ExceptionHandler espalhados de antes
em um único componente global de tratamento de erros.

Isso nos dá controle total sobre o corpo da resposta, bem como o código de status.
Ele fornece o mapeamento de várias exceções ao mesmo método, para serem tratadas em conjunto.
Ele faz bom uso da resposta RESTful ResposeEntity mais recente. */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                  HttpHeaders headers,
                  HttpStatus status,
                  WebRequest request) {

        var fieldErrors = ex.getBindingResult().getFieldErrors();
        var fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        var fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));

        return new ResponseEntity<>(ValidationErrorDetails.builder()
                .title("Field Validation Error")
                .field(fields)
                .fieldMessage(fieldMessages)
                .build(), headers, status);
    }
}
