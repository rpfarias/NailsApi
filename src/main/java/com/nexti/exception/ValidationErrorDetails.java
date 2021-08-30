package com.nexti.exception;

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
