package com.test.devsu.srvaccountmanager.handler;

import com.test.devsu.srvaccountmanager.dto.AccountDTO;
import com.test.devsu.srvaccountmanager.model.Account;
import com.test.devsu.srvaccountmanager.service.AccountService;
import com.test.devsu.srvaccountmanager.validation.ClassValidation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AccountHandler {

    private final AccountService accountService;
    private final ClassValidation validator;
    private final ModelMapper modelMapper;

    public Mono<ServerResponse> getAllAccounts(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountService.getAllAccounts(), Account.class);
    }

    public Mono<ServerResponse> getOneAccount(ServerRequest request) {
        Long accountId = Long.valueOf(request.pathVariable("id"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountService.getOneAccount(accountId), Account.class);
    }

    public Mono<ServerResponse> createAccount(ServerRequest request) {
        Mono<AccountDTO> accountDTOMono = request.bodyToMono(AccountDTO.class).doOnNext(validator::validate);
        return accountDTOMono.flatMap(acc -> {
            Account account = modelMapper.map(acc, Account.class);
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(accountService.saveAccount(account), Account.class);
        });
    }

    public Mono<ServerResponse> updateAccount(ServerRequest request) {
        Long accountId = Long.valueOf(request.pathVariable("id"));
        Mono<AccountDTO> accountDTOMono = request.bodyToMono(AccountDTO.class).doOnNext(validator::validate);
        return accountDTOMono.flatMap(acc -> {
            Account account = modelMapper.map(acc, Account.class);
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(accountService.updateAccount(accountId, account), Account.class);
        });
    }

    public Mono<ServerResponse> deleteAccount(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountService.deleteAccount(id),Void.class);
    }


}
