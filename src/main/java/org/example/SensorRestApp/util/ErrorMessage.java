package org.example.SensorRestApp.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorMessage {
    public static void errorMessage(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage()).append(";");
        }
        throw new MeasurementException(errorMessage.toString());
    }
}
