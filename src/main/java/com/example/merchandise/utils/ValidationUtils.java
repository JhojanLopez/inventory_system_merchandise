package com.example.merchandise.utils;

import lombok.experimental.UtilityClass;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class ValidationUtils {
    public Map<String, Object> getErrors(BindingResult result) {

        List<String> errors = result.getFieldErrors()
                .stream()
                .map(err -> err.getField() + " : " + err.getDefaultMessage())
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("message", errors);

        return response;
    }

}
