package com.account.control.controller;

import com.account.control.exception.CustomerNotFoundException;
import com.account.control.model.dto.AccountDto;
import com.account.control.model.dto.CustomerDto;
import com.account.control.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@Tag(name = "Customer Rest Controller", description = "Rest Controller of Customer")
public class CustomerRestController {

    @Autowired
    CustomerService customerService;

    @Operation(summary = "Get all customer information", description = "Get All Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful result",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AccountDto.class)))),
            @ApiResponse(responseCode = "204", description = "No customer found in the system"),
            @ApiResponse(responseCode = "500", description = "Something went wrong")
    })
    @GetMapping("/all")
    public ResponseEntity<?> getAllCustomer(){
        try {
            return ResponseEntity.ok(customerService.findAllCustomers());
        } catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get customer by customer id", description = "Return only one customer information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful result",
                    content = @Content(schema = @Schema(implementation = CustomerDto.class))),
            @ApiResponse(responseCode = "204", description = "No customer found in the system"),
            @ApiResponse(responseCode = "500", description = "Something went wrong")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer id){
        try {
            return ResponseEntity.ok(customerService.findCustomer(id));
        } catch (CustomerNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
