package com.example.bancking.exceptions;

public class BalanceNotSufficientException extends  Exception{
    public BalanceNotSufficientException(String message) {
        super(message);
    }
}
