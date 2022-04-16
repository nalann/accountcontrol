package com.account.control.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Integer id;
    private AccountCustomerDto customer;
    private BigDecimal initialCredit;
    private Set<TransactionDto> transactions;
    private LocalDateTime creationDate;
}
