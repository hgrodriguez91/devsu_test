package com.test.devsu.srvaccountmanager.handler;

import com.test.devsu.srvaccountmanager.dto.MovementDTO;
import com.test.devsu.srvaccountmanager.model.Account;
import com.test.devsu.srvaccountmanager.model.Movement;
import com.test.devsu.srvaccountmanager.service.MovementService;
import com.test.devsu.srvaccountmanager.validation.ClassValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MovementHandler {

    private final MovementService movementService;
    private final ClassValidation validator;

    public Mono<ServerResponse> getAllMovements(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(movementService.getAllMovement(), Account.class);
    }

    public Mono<ServerResponse> getOneMovement(ServerRequest request) {
        Long accountId = Long.valueOf(request.pathVariable("id"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(movementService.getOneMovement(accountId), Account.class);
    }

    public Mono<ServerResponse> saveMovement(ServerRequest request) {
        Mono<MovementDTO> client = request.bodyToMono(MovementDTO.class).doOnNext(validator::validate);
        return client.flatMap(mv -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(movementService.saveMovement(mv), Movement.class));

    }

    public Mono<ServerResponse> updateMovement(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        Mono<MovementDTO> client = request.bodyToMono(MovementDTO.class).doOnNext(validator::validate);
        return client.flatMap(mv -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(movementService.updateMovement(id, mv), Movement.class));

    }

    public Mono<ServerResponse> deleteMovement(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(movementService.deleteMovement(id),Void.class);
    }
}
