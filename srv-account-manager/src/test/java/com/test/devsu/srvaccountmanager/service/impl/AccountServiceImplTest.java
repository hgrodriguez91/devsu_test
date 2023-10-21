package com.test.devsu.srvaccountmanager.service.impl;

import com.test.devsu.srvaccountmanager.model.Account;
import com.test.devsu.srvaccountmanager.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AccountServiceImplTest testing")
class AccountServiceImplTest {


    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;
    private Account account;

    @BeforeEach
    void setUp() {
        account = Account.builder().id(1L)
                .clientId(1L)
                .balance(BigDecimal.valueOf(200))
                .type("CORRIENTE")
                .status(true)
                .build();
    }

    @Test
    @DisplayName("getAllAccounts should return a list")
    void getAllAccounts() {
        given(accountRepository.findAll()).willReturn(Arrays.asList(account));
        Flux<Account> accountFlux = accountService.getAllAccounts();
        StepVerifier.create(accountFlux)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("getOneAccount if exist should return an entity element")
    void getOneAccount() {
        given(accountRepository.findById(anyLong())).willReturn(Optional.of(account));
        Mono<Account> accountMono = accountService.getOneAccount(anyLong());
        StepVerifier.create(accountMono)
                .expectNext(account)
                .verifyComplete();
    }

    @Test
    @DisplayName("getOneAccount if not exist should return an error")
    void getOneAccountNotFout() {
        given(accountRepository.findById(anyLong())).willReturn(Optional.empty());
        Mono<Account> accountMono = accountService.getOneAccount(anyLong());
        StepVerifier.create(accountMono)
                .expectError()
                .verify();
    }

    @Test
    @DisplayName("Testing saveAccount method")
    void saveAccount() {
        given(accountRepository.findAccountByOldType(anyLong(), anyString())).willReturn(Optional.empty());
        given(accountRepository.save(any(Account.class))).willReturn(account);
        Mono<Account> accountMono = accountService.saveAccount(account);
        StepVerifier.create(accountMono)
                .expectNext(account)
                .verifyComplete();
    }

    @Test
    @DisplayName("Testing saveAccount method when given acount type exist")
    void saveAccountGivenTypeExiste() {
        given(accountRepository.findAccountByOldType(anyLong(), anyString())).willReturn(Optional.of(account));
        Mono<Account> accountMono = accountService.saveAccount(account);
        StepVerifier.create(accountMono)
                .expectError()
                .verify();
    }

    @Test
    @DisplayName("Testing updateAccount method")
    void updateAccount() {
        given(accountRepository.findById(anyLong())).willReturn(Optional.of(account));
        given(accountRepository.findAccountByClientIdAndType(anyLong(), anyLong(), anyString())).willReturn(Optional.empty());
        given(accountRepository.save(any(Account.class))).willReturn(account);
        Mono<Account> accountMono = accountService.updateAccount(1L, account);
        StepVerifier.create(accountMono)
                .expectNext(account)
                .verifyComplete();
    }

    @Test
    @DisplayName("Testing updateAccount method when account not found")
    void updateAccountNotFound() {
        given(accountRepository.findById(anyLong())).willReturn(Optional.empty());
        Mono<Account> accountMono = accountService.updateAccount(1L, account);
        StepVerifier.create(accountMono)
                .expectError()
                .verify();
    }

    @Test
    @DisplayName("Testing updateAccount method exist another account same type")
    void updateAccountAlreadyTypeExist() {
        given(accountRepository.findById(anyLong())).willReturn(Optional.of(account));
        given(accountRepository.findAccountByClientIdAndType(anyLong(), anyLong(), anyString())).willReturn(Optional.of(account));
        Mono<Account> accountMono = accountService.updateAccount(anyLong(), account);
        StepVerifier.create(accountMono)
                .expectError()
                .verify();
    }

    @Test
    @DisplayName("Testing deleteAccount method ")
    void deleteAccount() {
        given(accountRepository.findById(anyLong())).willReturn(Optional.empty());
        Mono<Void> voidMono = accountService.deleteAccount(anyLong());
        StepVerifier.create(voidMono)
                .expectError()
                .verify();
    }

}