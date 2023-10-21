package com.test.devsu.srvaccountmanager.handler;

import com.test.devsu.srvaccountmanager.dto.AccountDTO;
import com.test.devsu.srvaccountmanager.model.Account;
import com.test.devsu.srvaccountmanager.router.AccountRouter;
import com.test.devsu.srvaccountmanager.service.AccountService;
import com.test.devsu.srvaccountmanager.validation.ClassValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Arrays;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AccountHandler testing")
class AccountHandlerTest {

    private AccountService accountService;
    private ClassValidation validator;
    private WebTestClient webTestClient;
    private ModelMapper modelMapper;

    private Account account;

    @BeforeEach

    void setUp() {
        accountService = mock(AccountService.class);
        validator = mock(ClassValidation.class);
        modelMapper = mock(ModelMapper.class);
        AccountHandler accountHandler = new AccountHandler(accountService, validator,modelMapper);
        RouterFunction<ServerResponse> routes = new AccountRouter()
                .accountRoutes(accountHandler);
        webTestClient = WebTestClient
                .bindToRouterFunction(routes)
                .configureClient().codecs(codecs-> codecs.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder()))
                .build();

        account = Account.builder().id(1L)
                .clientId(1L)
                .balance(BigDecimal.valueOf(200))
                .type("CORRIENTE")
                .status(true)
                .build();
    }

    @Test
    @DisplayName("Test get all accounts")
    void getAllAccounts() {
        when(accountService.getAllAccounts()).thenReturn(Flux.fromIterable(Arrays.asList(account)));
        webTestClient.get().
                uri("/cuentas")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.length()").isEqualTo(1);
    }

    @Test
    @DisplayName("Test get account by id")
    void getOneAccount() {
        when(accountService.getOneAccount(anyLong())).thenReturn(Mono.just(account));
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/cuentas/{id}").build(1))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1);
    }

    @Test
    @DisplayName("Test create account")
    void createAccount() {
        when(modelMapper.map(any(AccountDTO.class),eq(Account.class))).thenReturn(account);
        when(accountService.saveAccount(any(Account.class))).thenReturn(Mono.just(account));
        webTestClient.post().uri("/cuentas")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(account), Account.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Account.class).isEqualTo(account);
    }

    @Test
    @DisplayName("Test update an account")
    void updateAccount() {
        when(modelMapper.map(any(AccountDTO.class),eq(Account.class))).thenReturn(account);
        when(accountService.updateAccount(anyLong(),any(Account.class))).thenReturn(Mono.just(account));
        webTestClient.put().uri(uriBuilder -> uriBuilder.path("/cuentas/{id}").build(1))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(account), Account.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Account.class).isEqualTo(account);
    }

    @Test
    @DisplayName("Test delete account")
    void deleteAccount() {
        Mockito.when(accountService.deleteAccount(anyLong())).thenReturn(Mono.empty());
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/cuentas/{id}").build(1))
                .exchange()
                .expectStatus().isOk();
    }
}