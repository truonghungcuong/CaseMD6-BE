package com.codegym.model.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    private String name;
    private String message;
    private String errorCode;

    public ErrorMessage(String message) {
        this.message = message;
    }
}
