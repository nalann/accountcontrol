package com.account.control.controller;

import com.account.control.exception.CustomerNotFoundException;
import com.account.control.model.entity.Account;
import com.account.control.model.request.CreateAccountRequest;
import com.account.control.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/account")
public class AccountRestController {

    @Autowired
    AccountService accountService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createNewAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        try {
            return new ResponseEntity<>(accountService.createNewAccount(createAccountRequest), HttpStatus.CREATED);
        } catch (CustomerNotFoundException exception){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
