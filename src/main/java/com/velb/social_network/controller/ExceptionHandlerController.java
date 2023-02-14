package com.velb.social_network.controller;

import com.velb.social_network.exception.PhotoNotFoundException;
import com.velb.social_network.exception.PostNotFoundException;
import com.velb.social_network.exception.UserAlreadyRegisteredException;
import com.velb.social_network.exception.UserNotFoundException;
import com.velb.social_network.exception.YandexException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RequestMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = PhotoNotFoundException.class)
    @ResponseBody
    public final ResponseEntity<Object> handleConstraintViolationEx(PhotoNotFoundException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(ex.getMessage(), headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PostNotFoundException.class)
    @ResponseBody
    public final ResponseEntity<Object> handleProductChangingEx(PostNotFoundException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(ex.getMessage(), headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserAlreadyRegisteredException.class)
    @ResponseBody
    public final ResponseEntity<Object> handleTotalRuntimeEx(UserAlreadyRegisteredException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(ex.getMessage(), headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseBody
    public final ResponseEntity<Object> handleUserAlreadyExistsEx(UserNotFoundException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(ex.getMessage(), headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = YandexException.class)
    @ResponseBody
    public final ResponseEntity<Object> handleBasketIsEmptyEx(YandexException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(ex.getMessage(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
