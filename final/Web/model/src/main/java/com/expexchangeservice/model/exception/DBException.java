package com.expexchangeservice.model.exception;

public class DBException extends Exception{
    public DBException(String errorMessage) {
        super(errorMessage);
    }
}
