package com.account.control.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
public class CustomerAccountDto {
    private Integer id;
    private BigDecimal initialCredit;
    private Set<TransactionDto> transactions;
    private LocalDateTime creationDate;
}
