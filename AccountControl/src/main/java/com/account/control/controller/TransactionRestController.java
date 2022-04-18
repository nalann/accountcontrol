package com.account.control.controller;

import com.account.control.exception.CustomerNotFoundException;
import com.account.control.exception.NotEnoughBalanceException;
import com.account.control.model.dto.AccountDto;
import com.account.control.model.dto.TransactionDto;
import com.account.control.model.request.NewTransactionRequest;
import com.account.control.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Tag(name = "Transaction Rest Controller", description = "Rest Controller of Transaction")
public class TransactionRestController {

    @Autowired
    TransactionService transactionService;

    Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Operation(summary = "Create New Transaction", description = "Create new transaction for existing customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully created",
                    content = @Content(schema = @Schema(implementation = TransactionDto.class))),
            @ApiResponse(responseCode = "400", description = "Not enough initial credit for this transaction"),
            @ApiResponse(responseCode = "204", description = "No customer found in the system"),
            @ApiResponse(responseCode = "500", description = "Something went wrong")
    })
    @PostMapping("/new")
    public ResponseEntity<?> createNewTransaction(@Valid @RequestBody NewTransactionRequest newTransactionRequest){
        try {
            return new ResponseEntity<>(transactionService.createNewTransaction(newTransactionRequest),
                    HttpStatus.CREATED);
        }
        catch (NotEnoughBalanceException exception){
            logger.error("Exception: " + exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (CustomerNotFoundException exception){
            logger.error("Exception: " + exception.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception exception){
            logger.error("Exception: " + exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
