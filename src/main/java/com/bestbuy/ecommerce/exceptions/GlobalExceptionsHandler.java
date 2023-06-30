package com.bestbuy.ecommerce.exceptions;

import com.bestbuy.ecommerce.dto.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
@ControllerAdvice
public class GlobalExceptionsHandler {


    @ExceptionHandler(CategoryNotFoundException.class)
    private ResponseEntity<ErrorResponse>categoryNotFoundException(CategoryNotFoundException categoryNotFoundException,
                                                                         WebRequest webRequest){
    ErrorResponse errorResponse = new ErrorResponse(new Date(), categoryNotFoundException.getMessage(), webRequest.getDescription(false));
      return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    private ResponseEntity<ErrorResponse>productNotFoundException(ProductNotFoundException productNotFoundException,
                                                                  WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(new Date(), productNotFoundException.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler(AppUserNotFountException.class)
    private ResponseEntity<ErrorResponse>appUserNotFoundException(AppUserNotFountException appUserNotFountException
            ,WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse( new Date(), appUserNotFountException.getMessage(),
                webRequest.getDescription(false));
        return  new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class) // handling error handling
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception exception,
                                                              WebRequest webRequest){
        ErrorResponse errorDetails = new ErrorResponse(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }




}
