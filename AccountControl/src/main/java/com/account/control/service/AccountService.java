package com.account.control.service;

import com.account.control.common.AccountConverter;
import com.account.control.model.dto.AccountDto;
import com.account.control.model.entity.Account;
import com.account.control.model.entity.Customer;
import com.account.control.model.request.CreateAccountRequest;
import com.account.control.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerService customerService;

    private final AccountConverter accountConverter = new AccountConverter();

    public AccountDto createNewAccount(CreateAccountRequest createAccountRequest){
        Customer customer = customerService.findCustomerById(createAccountRequest.getCustomerId());
        Account account = Account.builder()
                .customer(customer)
                .initialCredit(createAccountRequest.getInitialCredit())
                .creationDate(LocalDateTime.now())
                .build();
        customer.setBalance(customer.getBalance().add(createAccountRequest.getInitialCredit()));
        customerService.updateCustomer(customer);
        return accountConverter.convertAccountToAccountDto(accountRepository.save(account));
    }
}
