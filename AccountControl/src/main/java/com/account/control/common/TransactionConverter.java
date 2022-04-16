package com.account.control.common;

import com.account.control.model.dto.TransactionDto;
import com.account.control.model.entity.Transaction;

public class TransactionConverter {

    public TransactionDto convertTransactionToDto(Transaction transaction){
        return TransactionDto.builder()
                .transactionDate(transaction.getTransactionDate())
                .transactionAmount(transaction.getTransactionAmount())
                .transactionDetail(transaction.getTransactionDetail())
                .id(transaction.getId())
                .build();
    }
}
