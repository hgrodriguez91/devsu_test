package com.test.devsu.srvaccountmanager.service;

import com.test.devsu.srvaccountmanager.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

    Flux<Account> getAllAccounts();

    Mono<Account> getOneAccount(Long id);

    Mono<Account> saveAccount(Account account);

    Mono<Account> updateAccount(Long id, Account account);

    Mono<Void> deleteAccount(Long id);

}
