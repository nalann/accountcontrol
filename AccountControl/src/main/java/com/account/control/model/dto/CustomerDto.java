package com.account.control.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
public class CustomerDto {
    private Integer id;
    private String name;
    private String surname;
    private BigDecimal balance;
    private Set<CustomerAccountDto> accounts;
}
