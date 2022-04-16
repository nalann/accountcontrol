package com.account.control.common;

import com.account.control.model.dto.CustomerAccountDto;
import com.account.control.model.dto.CustomerDto;
import com.account.control.model.dto.TransactionDto;
import com.account.control.model.entity.Account;
import com.account.control.model.entity.Customer;
import com.account.control.model.entity.Transaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class CustomerConverterTest {

    private CustomerConverter customerConverter = null;
    private final LocalDateTime currentDate = LocalDateTime.now();

    @BeforeEach
    public void setUp(){
        customerConverter = new CustomerConverter();
    }

    @Test
    public void testConvertCustomerToCustomerDto(){
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

        Set<Account> accountSet = new HashSet<>();
        accountSet.add(account);
        customer.setAccounts(accountSet);

        TransactionDto transactionDto = TransactionDto.builder()
                .transactionAmount(BigDecimal.valueOf(24.4))
                .transactionDetail("test")
                .transactionDate(currentDate)
                .id(1)
                .build();

        Set<TransactionDto> transactionDtoSet = new HashSet<>();
        transactionDtoSet.add(transactionDto);

        CustomerAccountDto customerAccountDto = CustomerAccountDto.builder()
                .id(1)
                .creationDate(currentDate)
                .initialCredit(BigDecimal.valueOf(10.10))
                .transactions(transactionDtoSet)
                .build();

        Set<CustomerAccountDto> customerAccountDtoSet = new HashSet<>();
        customerAccountDtoSet.add(customerAccountDto);

        CustomerDto expectedResult = CustomerDto.builder()
                .id(1)
                .accounts(customerAccountDtoSet)
                .name("Name")
                .surname("Surname")
                .balance(BigDecimal.valueOf(10.10))
                .build();

        CustomerDto actualResult = customerConverter.convertCustomerToCustomerDto(customer);
        Assertions.assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);
    }
}
