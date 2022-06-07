package com.example.trns.service;

import com.example.trns.controller.request.TransferBalanceRequest;
import com.example.trns.dto.model.AccountStatement;
import com.example.trns.entity.Account;
import com.example.trns.entity.Transaction;

import java.util.List;

public interface AccountService {
    List<Account> findAll();
    Account save(Account account);
    Transaction sendMoney(
            TransferBalanceRequest transferBalanceRequest
    );
    AccountStatement getStatement(String accountNumber);
}