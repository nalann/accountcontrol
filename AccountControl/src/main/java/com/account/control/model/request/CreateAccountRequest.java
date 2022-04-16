package com.account.control.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {

    @NotNull
    private Integer customerId;

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal initialCredit;
}
