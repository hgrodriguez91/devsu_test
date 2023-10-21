package com.test.devsu.srvclientmanager.handler;

import com.test.devsu.srvclientmanager.dto.ClientDTO;
import com.test.devsu.srvclientmanager.dto.ReportDTO;
import com.test.devsu.srvclientmanager.model.Client;
import com.test.devsu.srvclientmanager.service.ClientService;
import com.test.devsu.srvclientmanager.util.Util;
import com.test.devsu.srvclientmanager.validation.ClassValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClientHandler {

    private final ClientService clientService;
    private final ClassValidation validator;

    public Mono<ServerResponse> getAllClients(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(clientService.getAll(), Client.class);
    }

    public Mono<ServerResponse> getOneClient(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(clientService.getById(id), Client.class);
    }

    public Mono<ServerResponse> saveClient(ServerRequest request) {
        Mono<ClientDTO> client = request.bodyToMono(ClientDTO.class).doOnNext(validator::validate);
        return client.flatMap(cl -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(clientService.save(cl), Client.class));

    }

    public Mono<ServerResponse> updateClient(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        Mono<ClientDTO> client = request.bodyToMono(ClientDTO.class).doOnNext(validator::validate);
        return client.flatMap(cl -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(clientService.update(id, cl), Client.class));

    }

    public Mono<ServerResponse> deleteClient(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(clientService.delete(id),Void.class);
    }

    public Mono<ServerResponse> getClientAccountReport(ServerRequest request) {
        MultiValueMap<String, String> queryParams = request.queryParams();
        Long id = Long.valueOf(request.pathVariable("id"));
        Date initDate = Util.getDateFromQueryParams(queryParams.get("fechaini").get(0));
        Date finalDate = Util.getDateFromQueryParams(queryParams.get("fechaend").get(0));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(clientService.generateReport(id, initDate, finalDate), ReportDTO.class);

    }
}
