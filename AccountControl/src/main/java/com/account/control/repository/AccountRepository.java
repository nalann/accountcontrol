package com.account.control.repository;

import com.account.control.model.entity.Account;
import com.account.control.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Set;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    public Account findFirstByCustomerAndInitialCreditGreaterThanEqualOrderByCreationDateDesc(
            Customer customer, BigDecimal initialCredit);
}
