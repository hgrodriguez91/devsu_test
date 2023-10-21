package com.test.devsu.srvclientmanager.service.impl;

import com.test.devsu.srvclientmanager.dto.AccountDTO;
import com.test.devsu.srvclientmanager.dto.ClientDTO;
import com.test.devsu.srvclientmanager.dto.ReportDTO;
import com.test.devsu.srvclientmanager.exception.CustomException;
import com.test.devsu.srvclientmanager.model.Client;
import com.test.devsu.srvclientmanager.repository.ClientRepository;
import com.test.devsu.srvclientmanager.service.ClientService;
import com.test.devsu.srvclientmanager.util.Util;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private static final String NOT_FOUND_MSG = "Client Not Found";
    private static final String DNI_IN_USE = "Given dni already in use by other client";

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;
    private final WebClient webClient;

    @Override
    public Flux<Client> getAll() {
        return Flux.fromIterable(clientRepository.findAll());
    }

    @Override
    public Mono<Client> getById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.map(Mono::just).orElseGet(() -> Mono.error(new CustomException(HttpStatus.NOT_FOUND, NOT_FOUND_MSG)));
    }

    @Override
    public Mono<Client> save(ClientDTO clientDto) {
        Optional<Client> existClient = clientRepository.findByDni(clientDto.getDni());
        if (existClient.isPresent()) {
            return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, DNI_IN_USE));
        }
        Client client = modelMapper.map(clientDto, Client.class);
        return Mono.just(clientRepository.save(client));
    }

    @Override
    public Mono<Client> update(Long id, ClientDTO clientDTO) {
        Optional<Client> client = clientRepository.findById(id);
        if (!client.isPresent()) {
            return Mono.error(new CustomException(HttpStatus.NOT_FOUND, NOT_FOUND_MSG));
        }
        Optional<Client> clientOpt = clientRepository.findAnotherClientDni(id, clientDTO.getDni());
        if (clientOpt.isPresent()) {
            return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, DNI_IN_USE));
        }
        Client updateClient = client.get();
        Util.mapfromDTO(updateClient, clientDTO);
        return Mono.just(clientRepository.save(updateClient));
    }

    @Override
    public Mono<Void> delete(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.<Mono<Void>>map(value -> Mono.create(m -> {
            clientRepository.delete(value);
            m.success();
        })).orElseGet(() -> Mono.error(new CustomException(HttpStatus.NOT_FOUND, NOT_FOUND_MSG)));
    }

    @Override
    public Mono<ReportDTO> generateReport(Long id, Date initialDate, Date FinalDate) {

        Optional<Client> client = clientRepository.findById(id);
        if (!client.isPresent()) {
            return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, NOT_FOUND_MSG));
        }
        ClientDTO clientDTO = modelMapper.map(client.get(), ClientDTO.class);
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setCliente(clientDTO);
        Mono<ReportDTO> reportMono = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/acccount-status/{clientId}")
                        .queryParam("fechaini", "{initialDate}")
                        .queryParam("fechaend", "{FinalDate}")
                        .build(id, initialDate, FinalDate))
                .retrieve()
                .bodyToMono(ReportDTO.class).doOnNext(m -> m.setCliente(clientDTO));
        return reportMono;


    }

}

