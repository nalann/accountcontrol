package com.account.control.controller;

import com.account.control.exception.CustomerNotFoundException;
import com.account.control.exception.NotEnoughBalanceException;
import com.account.control.model.request.NewTransactionRequest;
import com.account.control.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/transaction")
public class TransactionRestController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/new")
    public ResponseEntity<?> createNewTransaction(@Valid @RequestBody NewTransactionRequest newTransactionRequest){
        try {
            return new ResponseEntity<>(transactionService.createNewTransaction(newTransactionRequest),
                    HttpStatus.CREATED);
        }
        catch (NotEnoughBalanceException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (CustomerNotFoundException exception){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
