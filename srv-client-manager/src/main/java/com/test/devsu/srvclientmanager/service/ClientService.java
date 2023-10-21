package com.test.devsu.srvclientmanager.service;

import com.test.devsu.srvclientmanager.dto.ClientDTO;
import com.test.devsu.srvclientmanager.dto.ReportDTO;
import com.test.devsu.srvclientmanager.model.Client;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.Date;


public interface ClientService {

    Flux<Client> getAll();

    Mono<Client> getById(Long id);

    Mono<Client> save(ClientDTO clientDto);

    Mono<Client> update(Long id, ClientDTO clientDTO);

    Mono<Void> delete(Long id);

    Mono<ReportDTO> generateReport(Long id, Date initialDate, Date FinalDate);
}
