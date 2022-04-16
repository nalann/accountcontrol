package com.account.control.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewTransactionRequest {

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal transactionAmount;

    @NotNull
    private Integer customerId;

    @NotNull
    private String detail;
}
