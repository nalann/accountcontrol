package com.account.control.common;

import com.account.control.model.dto.CustomerAccountDto;
import com.account.control.model.dto.CustomerDto;
import com.account.control.model.entity.Customer;

import java.util.HashSet;
import java.util.Set;

public class CustomerConverter {

    private final AccountConverter accountConverter = new AccountConverter();

    public CustomerDto convertCustomerToCustomerDto(Customer customer){
        Set<CustomerAccountDto> customerAccountDtos = new HashSet<>();
        if(customer.getAccounts() != null){
            customer.getAccounts().stream().forEach(customerAccount -> {
                customerAccountDtos.add(accountConverter.convertAccountToCustomerAccount(customerAccount));
            });
        }
        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .accounts(customerAccountDtos)
                .balance(customer.getBalance())
                .surname(customer.getSurname())
                .build();
    }
}
