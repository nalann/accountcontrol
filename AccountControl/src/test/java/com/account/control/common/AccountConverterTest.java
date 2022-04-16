package com.account.control.common;

import com.account.control.model.dto.AccountCustomerDto;
import com.account.control.model.dto.AccountDto;
import com.account.control.model.dto.CustomerAccountDto;
import com.account.control.model.dto.TransactionDto;
import com.account.control.model.entity.Account;
import com.account.control.model.entity.Customer;
import com.account.control.model.entity.Transaction;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class AccountConverterTest {

    private AccountConverter accountConverter = null;
    private final LocalDateTime currentDate = LocalDateTime.now();

    @BeforeEach
    public void setUp(){
        accountConverter = new AccountConverter();
    }

    @Test
    public void testConvertAccountToAccountDto(){
        Customer customer = Customer.builder()
                .id(1)
                .name("Name")
                .surname("Surname")
                .balance(BigDecimal.valueOf(10.10))
                .build();
        Account account = Account.builder()
                .id(1)
                .initialCredit(BigDecimal.valueOf(10.10))
                .customer(customer)
                .creationDate(currentDate)
                .transactions(null)
                .build();
        Transaction transaction = Transaction.builder()
                .id(1)
                .transactionDate(currentDate)
                .transactionDetail("test")
                .transactionAmount(BigDecimal.valueOf(24.4))
                .account(account)
                .build();
        Set<Transaction> transactionSet = new HashSet<>();
        transactionSet.add(transaction);

        account.setTransactions(transactionSet);

        AccountCustomerDto accountCustomerDto = AccountCustomerDto.builder()
                .id(1)
                .surname("Surname")
                .name("Name")
                .build();

        AccountDto expectedResult = AccountDto.builder()
                .id(1)
                .creationDate(currentDate)
                .initialCredit(BigDecimal.valueOf(10.10))
                .customer(accountCustomerDto)
                .transactions(null)
                .build();
        TransactionDto transactionDto = TransactionDto.builder()
                .transactionAmount(BigDecimal.valueOf(24.4))
                .transactionDetail("test")
                .transactionDate(currentDate)
                .id(1)
                .build();
        Set<TransactionDto> transactionDtoSet = new HashSet<>();
        transactionDtoSet.add(transactionDto);
        expectedResult.setTransactions(transactionDtoSet);

        AccountDto actualResult = accountConverter.convertAccountToAccountDto(account);
        Assertions.assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    public void testConvertAccountToCustomerAccount(){
        Customer customer = Customer.builder()
                .id(1)
                .name("Name")
                .surname("Surname")
                .balance(BigDecimal.valueOf(10.10))
                .build();
        Account account = Account.builder()
                .id(1)
                .initialCredit(BigDecimal.valueOf(10.10))
                .customer(customer)
                .creationDate(currentDate)
                .transactions(null)
                .build();
        Transaction transaction = Transaction.builder()
                .id(1)
                .transactionDate(currentDate)
                .transactionDetail("test")
                .transactionAmount(BigDecimal.valueOf(24.4))
                .account(account)
                .build();
        Set<Transaction> transactionSet = new HashSet<>();
        transactionSet.add(transaction);

        account.setTransactions(transactionSet);

        TransactionDto transactionDto = TransactionDto.builder()
                .transactionAmount(BigDecimal.valueOf(24.4))
                .transactionDetail("test")
                .transactionDate(currentDate)
                .id(1)
                .build();

        Set<TransactionDto> transactionDtoSet = new HashSet<>();
        transactionDtoSet.add(transactionDto);

        CustomerAccountDto expectedResult = CustomerAccountDto.builder()
                .id(1)
                .creationDate(currentDate)
                .initialCredit(BigDecimal.valueOf(10.10))
                .transactions(transactionDtoSet)
                .build();

        CustomerAccountDto actualResult = accountConverter.convertAccountToCustomerAccount(account);
        Assertions.assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);
    }
}
