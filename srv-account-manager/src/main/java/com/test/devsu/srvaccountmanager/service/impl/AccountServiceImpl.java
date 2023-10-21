package com.test.devsu.srvaccountmanager.service.impl;


import com.test.devsu.srvaccountmanager.exception.CustomException;
import com.test.devsu.srvaccountmanager.model.Account;
import com.test.devsu.srvaccountmanager.repository.AccountRepository;
import com.test.devsu.srvaccountmanager.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private static final String NOT_FOUND_MSG = "Account not found";
    private static final String CLIENT_ACCOUNT_TYPE_MSG = "Client already have an account of the given type";

    private final AccountRepository accountRepository;

    @Override
    public Flux<Account> getAllAccounts() {
        return Flux.fromIterable(accountRepository.findAll());
    }

    @Override
    public Mono<Account> getOneAccount(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        return accountOptional.map(Mono::just)
                .orElseGet(() -> Mono.error(new CustomException(HttpStatus.NOT_FOUND, NOT_FOUND_MSG)));
    }

    @Override
    public Mono<Account> saveAccount(Account account) {
        Optional<Account> existTypeAccount = accountRepository
                .findAccountByOldType(account.getClientId(), account.getType());
        if (existTypeAccount.isPresent())
            return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, CLIENT_ACCOUNT_TYPE_MSG));
        return Mono.just(accountRepository.save(account));
    }

    @Override
    public Mono<Account> updateAccount(Long id, Account account) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (!accountOptional.isPresent())
            return Mono.error(new CustomException(HttpStatus.NOT_FOUND, NOT_FOUND_MSG));
        Optional<Account> existTypeAccount = accountRepository
                .findAccountByClientIdAndType(accountOptional.get().getId(),account.getClientId(), account.getType());
        if (existTypeAccount.isPresent())
            return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, CLIENT_ACCOUNT_TYPE_MSG));
        Account mapAccount = mapDtoToModel(accountOptional.get(), account);
        return Mono.just(accountRepository.save(mapAccount));
    }

    private Account mapDtoToModel(Account account, Account accountDTO) {
        account.setBalance(accountDTO.getBalance() != null ? accountDTO.getBalance() : account.getBalance());
        account.setType(accountDTO.getType() != null ? accountDTO.getType() : account.getType());
        account.setStatus(accountDTO.getStatus() != null ? accountDTO.getStatus() : account.getStatus());
        return account;
    }

    @Override
    public Mono<Void> deleteAccount(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        return accountOptional.<Mono<Void>>map(account -> Mono.create(m -> {
            accountRepository.delete(account);
            m.success();
        })).orElseGet(() -> Mono.error(new CustomException(HttpStatus.NOT_FOUND, NOT_FOUND_MSG)));
    }
}
