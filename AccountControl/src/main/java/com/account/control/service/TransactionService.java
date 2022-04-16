package com.account.control.service;

import com.account.control.common.TransactionConverter;
import com.account.control.exception.CustomerNotFoundException;
import com.account.control.exception.NotEnoughBalanceException;
import com.account.control.model.dto.TransactionDto;
import com.account.control.model.entity.Account;
import com.account.control.model.entity.Customer;
import com.account.control.model.entity.Transaction;
import com.account.control.model.request.NewTransactionRequest;
import com.account.control.repository.AccountRepository;
import com.account.control.repository.CustomerRepository;
import com.account.control.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    private final TransactionConverter transactionConverter = new TransactionConverter();

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    public TransactionDto createNewTransaction(NewTransactionRequest newTransactionRequest){
        Optional<Customer> customer = customerRepository.findById(newTransactionRequest.getCustomerId());
        if(customer.isPresent()){
            //Using last created account which has enough limit to transaction.
            Account account = accountRepository
                    .findFirstByCustomerAndInitialCreditGreaterThanEqualOrderByCreationDateDesc(
                            customer.get(),
                            newTransactionRequest.getTransactionAmount());
            if(account == null){
                throw new NotEnoughBalanceException("Not enough balance for this customer: "
                        + newTransactionRequest.getCustomerId());
            }
            //Decrease account credit
            account.setInitialCredit(account.getInitialCredit()
                    .subtract(newTransactionRequest.getTransactionAmount()));
            accountRepository.save(account);
            //Decrease customer balance
            customer.get().setBalance(customer.get().getBalance()
                    .subtract(newTransactionRequest.getTransactionAmount()));
            customerRepository.save(customer.get());
            //Save transaction
            Transaction transaction = Transaction.builder()
                    .transactionAmount(newTransactionRequest.getTransactionAmount())
                    .account(account)
                    .transactionDate(LocalDateTime.now())
                    .transactionDetail(newTransactionRequest.getDetail())
                    .build();
            return transactionConverter.convertTransactionToDto(transactionRepository.save(transaction));
        }
        else{
            throw new CustomerNotFoundException("Customer not found: " + newTransactionRequest.getCustomerId());
        }
    }

}
