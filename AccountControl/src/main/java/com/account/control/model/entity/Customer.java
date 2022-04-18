package com.account.control.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@Entity(name = "customer")
public class Customer {

    @Schema(description = "Customer ID", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Customer Name", required = true)
    private String name;

    @Schema(description = "Customer Surname", required = true)
    private String surname;

    @Schema(description = "Customer total credit information", required = true)
    private BigDecimal balance;

    @Schema(description = "Customer's Accounts Information")
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Account> accounts;
}
