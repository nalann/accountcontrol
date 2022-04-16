package com.account.control.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountCustomerDto {
    private Integer id;
    private String name;
    private String surname;
}
