package com.account.control.service;

import com.account.control.common.AccountConverter;
import com.account.control.model.dto.AccountDto;
import com.account.control.model.entity.Account;
import com.account.control.model.entity.Customer;
import com.account.control.model.request.CreateAccountRequest;
import com.account.control.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(AccountService.class);

    public AccountDto createNewAccount(CreateAccountRequest createAccountRequest){
        logger.info("create new account started");
        logger.debug("create account request send for : " + createAccountRequest.getCustomerId());
        Customer customer = customerService.findCustomerById(createAccountRequest.getCustomerId());
        Account account = Account.builder()
                .customer(customer)
                .initialCredit(createAccountRequest.getInitialCredit())
                .creationDate(LocalDateTime.now())
                .build();
        customer.setBalance(customer.getBalance().add(createAccountRequest.getInitialCredit()));
        customerService.updateCustomer(customer);
        logger.debug("customer updated : " + customer);
        logger.debug("account saved : " + account);
        return accountConverter.convertAccountToAccountDto(accountRepository.save(account));
    }
}
