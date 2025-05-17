package com.example.bancking.repositories;

import com.example.bancking.entities.AccountOperation;
import com.example.bancking.entities.BanckAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {

    public List<AccountOperation> findByBanckAccountId(String accountId);
    public Page<AccountOperation> findByBanckAccountId(String accountId, Pageable pageable);


}
