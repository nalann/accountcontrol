package com.account.control.common;

import com.account.control.model.dto.TransactionDto;
import com.account.control.model.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionConverter {

    Logger logger = LoggerFactory.getLogger(TransactionConverter.class);

    public TransactionDto convertTransactionToDto(Transaction transaction){
        logger.debug("convert from transation to transactiondto");
        return TransactionDto.builder()
                .transactionDate(transaction.getTransactionDate())
                .transactionAmount(transaction.getTransactionAmount())
                .transactionDetail(transaction.getTransactionDetail())
                .id(transaction.getId())
                .build();
    }
}
