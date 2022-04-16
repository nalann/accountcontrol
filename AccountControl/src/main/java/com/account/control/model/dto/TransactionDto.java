package com.account.control.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TransactionDto {
    private Integer id;
    private String transactionDetail;
    private BigDecimal transactionAmount;
    private LocalDateTime transactionDate;
}
