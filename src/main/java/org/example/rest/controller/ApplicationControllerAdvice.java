package org.example.rest.controller;

import org.example.exception.RegraNegocioException;
import org.example.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException exception){
        String mensagemErro = exception.getMessage();
        return new ApiErrors(mensagemErro);
    }
}
