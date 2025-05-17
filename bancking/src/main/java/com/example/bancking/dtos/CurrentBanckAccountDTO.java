package com.example.bancking.dtos;

import com.example.bancking.enums.AccountStatus;
import lombok.Data;

import java.util.Date;

@Data

public class CurrentBanckAccountDTO extends  BanckAccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double overDraft;


}
