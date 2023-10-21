package com.test.devsu.srvclientmanager.handler;

import com.test.devsu.srvclientmanager.dto.ClientDTO;
import com.test.devsu.srvclientmanager.model.Client;
import com.test.devsu.srvclientmanager.router.ClientRouter;
import com.test.devsu.srvclientmanager.service.ClientService;
import com.test.devsu.srvclientmanager.validation.ClassValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.*;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
@DisplayName("Integration test ClientHandler controller")
class ClientHandlerTest {


    private ClientService clientService;
    private ClassValidation validator;
    private WebTestClient webTestClient;

    private ClientDTO clientDTO;
    private Client client = new Client();

    @BeforeEach
    void setUp() {
        clientService = mock(ClientService.class);
        validator = mock(ClassValidation.class);
        ClientHandler userHandler = new ClientHandler(clientService, validator);
        RouterFunction<ServerResponse> routes = new ClientRouter()
                .router(userHandler);
        webTestClient = WebTestClient
                .bindToRouterFunction(routes).configureClient().codecs(codecs-> codecs.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder()))
                .build();


        clientDTO = ClientDTO.builder()
                .name("Jhon Doe")
                .age(32)
                .phoneNumber("123456789")
                .gender('M')
                .address("Boulevar Street")
                .password("password")
                .status(true)
                .dni("92112345982").build();

        client = Client.builder().clientId(1L).password("password").status(true).build();
        client.setName("Jhon Doe");
        client.setAge(32);
        client.setPhoneNumber("123456789");
        client.setGender('M');
        client.setAddress("Boulevar Street");
        client.setDni("92112345982");
    }

    @Test
    @DisplayName("Testing getAllClients method ")
    void getAllClients() {
        when(clientService.getAll()).thenReturn(Flux.fromIterable(Arrays.asList(client)));
        webTestClient.get().
                uri("/clientes")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.length()").isEqualTo(1);
    }

    @Test
    @DisplayName("Testing getOneClient method ")
    void getOneClient() {
        Mockito.when(clientService.getById(anyLong())).thenReturn(Mono.just(client));
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/clientes/{id}").build(1))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.clientId").isEqualTo(1);


    }

    @Test
    @DisplayName("Testing saveClient method ")
    void saveClient() {
        Mockito.when(clientService.save(any(ClientDTO.class))).thenReturn(Mono.just(client));
        webTestClient.post().uri("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(clientDTO), ClientDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Client.class).isEqualTo(client);
    }

    @Test
    @DisplayName("Testing updateClient method")
    void updateClient() {
        Mockito.when(clientService.update(anyLong(),any(ClientDTO.class))).thenReturn(Mono.just(client));
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder.path("/clientes/{id}").build(1))
                .body(Mono.just(clientDTO), ClientDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.clientId").isEqualTo(1);
    }

    @Test
    @DisplayName("Testing deleteClient method")
    void deleteClient() {
        Mockito.when(clientService.delete(anyLong())).thenReturn(Mono.empty());
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/clientes/{id}").build(1))
                .exchange()
                .expectStatus().isOk();
    }
}