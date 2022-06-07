package com.example.trns.controller.api;

import com.example.trns.controller.request.AccountStatementRequest;
import com.example.trns.controller.request.TransferBalanceRequest;
import com.example.trns.dto.model.response.Response;
import com.example.trns.entity.Account;
import com.example.trns.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @RequestMapping("/create")
    public List<Account> create(@RequestBody Account account) {
        accountService.save(account);
        return accountService.findAll();
    }

    @RequestMapping("/all")
    public List<Account> all() {
        return accountService.findAll();
    }

    @RequestMapping("/sendmoney")
    public Response sendMoney(
            @RequestBody TransferBalanceRequest transferBalanceRequest
    ) {

        return Response.ok().setPayload(
                accountService.sendMoney(
                        transferBalanceRequest
                )
        );
    }
    @RequestMapping("/statement")
    public Response getStatement(
            @RequestBody AccountStatementRequest accountStatementRequest

    ){
        return Response.ok().setPayload(
                accountService.getStatement(accountStatementRequest.getAccountNumber())
        );

    }

}