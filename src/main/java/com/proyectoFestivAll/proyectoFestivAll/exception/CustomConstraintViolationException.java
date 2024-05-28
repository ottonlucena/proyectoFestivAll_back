package com.proyectoFestivAll.proyectoFestivAll.exception;

public class CustomConstraintViolationException extends RuntimeException{
    public CustomConstraintViolationException(String message){
        super(message);
    }
}
