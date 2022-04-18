package com.account.control.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@Entity(name = "account")
public class Account {

    @Schema(description = "Account ID", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Related Customer Information", required = true)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer", nullable = false)
    private Customer customer;

    @Schema(description = "Account Credit", required = true)
    private BigDecimal initialCredit;

    @Schema(description = "Account Creation Date", required = true)
    private LocalDateTime creationDate;

    @Schema(description = "Related Transactions Information")
    @OneToMany(mappedBy = "account" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Transaction> transactions;
}
