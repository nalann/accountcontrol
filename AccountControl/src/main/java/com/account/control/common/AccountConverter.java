package com.account.control.common;

import com.account.control.model.dto.AccountCustomerDto;
import com.account.control.model.dto.AccountDto;
import com.account.control.model.dto.CustomerAccountDto;
import com.account.control.model.dto.TransactionDto;
import com.account.control.model.entity.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class AccountConverter {

    private final TransactionConverter transactionConverter = new TransactionConverter();

    Logger logger = LoggerFactory.getLogger(AccountConverter.class);

    public AccountDto convertAccountToAccountDto(Account account){
        logger.debug("convert from account to accountdto");
        AccountCustomerDto accountCustomerDto = null;
        Set<TransactionDto> transactionDtos = new HashSet<>();
        if(account.getCustomer() != null) {
            accountCustomerDto = AccountCustomerDto.builder()
                    .name(account.getCustomer().getName())
                    .surname(account.getCustomer().getSurname())
                    .id(account.getCustomer().getId()).build();
        }
        if(account.getTransactions() != null) {
            account.getTransactions().stream().forEach(transaction -> {
                transactionDtos.add(transactionConverter.convertTransactionToDto(transaction));
            });
        }
        return AccountDto.builder().id(account.getId())
                .customer(accountCustomerDto)
                .initialCredit(account.getInitialCredit())
                .transactions(transactionDtos)
                .creationDate(account.getCreationDate())
                .build();
    }

    public CustomerAccountDto convertAccountToCustomerAccount(Account account){
        logger.debug("convert from account to customeraccountdto");
        Set<TransactionDto> transactionDtos = new HashSet<>();
        if(account.getTransactions() != null){
            account.getTransactions().stream().forEach(transaction -> {
                transactionDtos.add(transactionConverter.convertTransactionToDto(transaction));
            });
        }
        return CustomerAccountDto.builder()
                .initialCredit(account.getInitialCredit())
                .id(account.getId())
                .transactions(transactionDtos)
                .creationDate(account.getCreationDate())
                .build();
    }
}
