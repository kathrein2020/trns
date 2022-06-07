package com.example.trns.banking.service.impl;

import com.example.trns.controller.request.TransferBalanceRequest;
import com.example.trns.entity.Account;
import com.example.trns.service.impl.AccountServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountServiceImplTest {

    @TestConfiguration
    static class AccountServiceTestContextConfiguration {
        @Bean
        public AccountServiceImpl accountServiceImplTest() {
            return new AccountServiceImpl();

        }
    }

    @Autowired
    private AccountServiceImpl accountService;

    @Test
    public void sendMoneyTest() {
        Account account1 = new Account(0L, "1001", new BigDecimal(50000));
        Account account2 = new Account(0L, "2002", new BigDecimal(2000));
        accountService.save(account1);
        accountService.save(account2);

        TransferBalanceRequest transferBalanceRequest =
                new TransferBalanceRequest(
                        account1.getAccountNumber(),
                        account2.getAccountNumber(),
                        new BigDecimal(3000)
                );
        accountService.sendMoney(transferBalanceRequest);
        assertThat(accountService.findByAccountNumber(account1.getAccountNumber())
                .getCurrentBalance())
                .isEqualTo(new BigDecimal(47000));
        assertThat(accountService.findByAccountNumber(account2.getAccountNumber())
                .getCurrentBalance())
                .isEqualTo(new BigDecimal(5000));

    }

    @Test
    public void getStatement() {
        Account account1 = new Account(0L, "1001", new BigDecimal(50000));
        Account account2 = new Account(0L, "2002", new BigDecimal(2000));
        accountService.save(account1);
        accountService.save(account2);
        TransferBalanceRequest transferBalanceRequest =
                new TransferBalanceRequest(
                        account1.getAccountNumber(),
                        account2.getAccountNumber(),
                        new BigDecimal(3000)
                );

        accountService.sendMoney(transferBalanceRequest);
        assertThat(accountService.getStatement(account1.getAccountNumber())
                .getCurrentBalance())
                .isEqualTo(new BigDecimal(47000));
        accountService.sendMoney(transferBalanceRequest);
        assertThat(accountService.getStatement(account1.getAccountNumber())
                .getCurrentBalance()).isEqualTo(new BigDecimal(44000));
        assertThat(accountService.getStatement(account1.getAccountNumber())
                .getTransactionHistory().size()).isEqualTo(2);
    }

}