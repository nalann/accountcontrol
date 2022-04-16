package com.account.control.model.entity;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer", nullable = false)
    private Customer customer;

    private BigDecimal initialCredit;

    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "account" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Transaction> transactions;
}
