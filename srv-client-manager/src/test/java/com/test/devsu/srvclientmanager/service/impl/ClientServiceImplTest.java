package com.test.devsu.srvclientmanager.service.impl;

import com.test.devsu.srvclientmanager.dto.ClientDTO;
import com.test.devsu.srvclientmanager.model.Client;
import com.test.devsu.srvclientmanager.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Optional;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClientService testing")
class ClientServiceImplTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client = new Client();


    @BeforeEach
    void setUp() {
        client.setClientId(1L);
        client.setName("Jhon Doe");
        client.setAge(32);
        client.setPhoneNumber("123456789");
        client.setGender('M');
        client.setAddress("Boulevar Street");
        client.setPassword("password");
        client.setStatus(true);
        client.setDni("92112345982");

    }

    @Test
    @DisplayName("Testing getAll method")
    void getAll() {
        given(clientRepository.findAll()).willReturn(Arrays.asList(client));
        Flux<Client> clientList = clientService.getAll();
        StepVerifier.create(clientList)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Testing getById method")
    void getById() {
        given(clientRepository.findById(1L)).willReturn(Optional.of(client));
        Mono<Client> clientMono = clientService.getById(1L);
        StepVerifier.create(clientMono)
                .expectNext(client)
                .verifyComplete();
    }

    @Test
    @DisplayName("Testing save method")
    void save() {
        given(clientRepository.save(any(Client.class))).willReturn(client);
        given(clientRepository.findByDni("92112345982")).willReturn(Optional.empty());
        ClientDTO clientDTO = ClientDTO.builder()
                .name("Jhon Doe").age(32).dni("92112345982")
                .address("Boulevar Street").gender('M').phoneNumber("123456789")
                .password("password").status(true).build();
        given(modelMapper.map(clientDTO, Client.class)).willReturn(client);
        Mono<Client> clientMono = clientService.save(clientDTO);
        StepVerifier.create(clientMono)
                .expectNext(client)
                .verifyComplete();
    }

    @Test
    @DisplayName("Testing save method when field dni already in use")
    void saveWhenClientDniInUse() {
        given(clientRepository.findByDni("92112345982")).willReturn(Optional.of(client));
        ClientDTO clientDTO = ClientDTO.builder()
                .name("Jhon Doe").age(32).dni("92112345982")
                .address("Boulevar Street").gender('M').phoneNumber("123456789")
                .password("password").status(true).build();
        Mono<Client> clientMono = clientService.save(clientDTO);
        StepVerifier.create(clientMono)
                .expectError()
                .verify();
    }

    @Test
    @DisplayName("Testing update method")
    void update() {
        Client client2 = Client.builder()
                .clientId(1L)
                .password("password")
                .status(true).build();
        client2.setDni("92112345982");
        client2.setName("Jhon Wick");
        client2.setAge(33);
        client2.setPhoneNumber("5351235698");
        client2.setGender('M');
        client2.setAddress("Boulevar Street");

        given(clientRepository.findById(1L)).willReturn(Optional.of(client));
        ClientDTO clientDTO = ClientDTO.builder()
                .name("Jhon Wick").age(33).dni("92112345982")
                .address("Boulevar Street").gender('M').phoneNumber("5351235698")
                .password("password").status(true).build();
        given(clientRepository.save(any(Client.class))).willReturn(client2);

        Mono<Client> updatedClient = clientService.update(1L, clientDTO);
        StepVerifier.create(updatedClient)
                .expectNext(client2)
                .verifyComplete();
    }

    @Test
    @DisplayName("Testing update method when client not found")
    void updateClientNotFound() {
        given(clientRepository.findById(1L)).willReturn(Optional.empty());
        ClientDTO clientDTO = ClientDTO.builder()
                .name("Jhon Wick").age(32).dni("92112345982")
                .address("Boulevar Street").gender('M').phoneNumber("123456789")
                .password("password").status(true).build();
        Mono<Client> updatedClient = clientService.update(1L, clientDTO);
        StepVerifier.create(updatedClient)
                .expectError()
                .verify();
    }

    @Test
    @DisplayName("Testing update method when dni already in use")
    void updateClientAlreadyDniUsed() {

        Client client2 = Client.builder()
                .clientId(1L)
                .password("password")
                .status(true).build();
        client2.setDni("92112345982");
        client2.setName("Jhon Wick");
        client2.setAge(33);
        client2.setPhoneNumber("5351235698");
        client2.setGender('M');
        client2.setAddress("Boulevar Street");

        given(clientRepository.findById(1L)).willReturn(Optional.of(client));
        given(clientRepository.findAnotherClientDni(1L, "92112345982")).willReturn(Optional.of(client2));
        ClientDTO clientDTO = ClientDTO.builder()
                .name("Jhon Wick").age(32).dni("92112345982")
                .address("Boulevar Street").gender('M').phoneNumber("123456789")
                .password("password").status(true).build();
        Mono<Client> updatedClient = clientService.update(1L, clientDTO);
        StepVerifier.create(updatedClient)
                .expectError()
                .verify();
    }

    @Test
    @DisplayName("Testing delete method when client not found")
    void delete() {
        given(clientRepository.findById(anyLong())).willReturn(Optional.empty());
        Mono<Void> updatedClient = clientService.delete(anyLong());
        StepVerifier.create(updatedClient)
                .expectError()
                .verify();
    }


}