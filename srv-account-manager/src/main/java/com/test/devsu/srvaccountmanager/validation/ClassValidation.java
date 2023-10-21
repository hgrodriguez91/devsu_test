package com.test.devsu.srvaccountmanager.validation;

import com.test.devsu.srvaccountmanager.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClassValidation {

    private final Validator validator;

    @SneakyThrows
    public <T> T validate(T object) {
        Set<ConstraintViolation<T>> errors = validator.validate(object);
        if (errors.isEmpty()) {
            return object;
        } else {
            String message = errors.stream().map(err -> err.getMessage()).collect(Collectors.joining(", "));
            throw new CustomException(HttpStatus.BAD_REQUEST, message);
        }
    }
}
