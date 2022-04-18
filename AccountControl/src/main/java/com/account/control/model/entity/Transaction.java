package com.account.control.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@Entity
public class Transaction {

    @Schema(description = "Transaction ID", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Transaction Details")
    private String transactionDetail;

    @Schema(description = "Transaction Amount", required = true)
    private BigDecimal transactionAmount;

    @Schema(description = "Transaction Creation Date")
    private LocalDateTime transactionDate;

    @Schema(description = "Related Account Information. Holds which account have been used for this transaction")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account", nullable = false)
    private Account account;
}
