package com.account.control.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotEnoughBalanceException extends RuntimeException{

    public NotEnoughBalanceException(String exceptionMsg){
        super(exceptionMsg);
    }

}
