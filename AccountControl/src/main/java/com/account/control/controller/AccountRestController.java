package com.account.control.controller;

import com.account.control.exception.CustomerNotFoundException;
import com.account.control.model.dto.AccountDto;
import com.account.control.model.entity.Account;
import com.account.control.model.request.CreateAccountRequest;
import com.account.control.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
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
@Tag(name = "Account Rest Controller", description = "Rest Controller of Account")
public class AccountRestController {

    @Autowired
    AccountService accountService;

    @Operation(summary = "Create New Account", description = "Create new account for existing customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully created",
                    content = @Content(schema = @Schema(implementation = AccountDto.class))),
            @ApiResponse(responseCode = "204", description = "No customer found in the system"),
            @ApiResponse(responseCode = "500", description = "Something went wrong")
    })
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
