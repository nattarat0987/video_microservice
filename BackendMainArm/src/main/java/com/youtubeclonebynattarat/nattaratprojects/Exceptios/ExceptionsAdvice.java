package com.youtubeclonebynattarat.nattaratprojects.Exceptios;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalTime;

@ControllerAdvice
public class ExceptionsAdvice {
    @ExceptionHandler({BaseException.class})
    public ResponseEntity<ErrorResponse>   ExceptionHandler(BaseException exceptionFile){
        ErrorResponse  errorResponse = new ErrorResponse();
        errorResponse.setError(exceptionFile.getMessage());
        errorResponse.setStatus(HttpStatus.EXPECTATION_FAILED.value());
        return new ResponseEntity<>(errorResponse,HttpStatus.EXPECTATION_FAILED);
    }
    @Data
    private  static class ErrorResponse{
         String error;
        Integer status;
        LocalTime localTime = LocalTime.now();
    }


}
