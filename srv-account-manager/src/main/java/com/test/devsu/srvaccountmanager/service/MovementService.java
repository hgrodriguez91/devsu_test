package com.test.devsu.srvaccountmanager.service;

import com.test.devsu.srvaccountmanager.dto.MovementDTO;
import com.test.devsu.srvaccountmanager.model.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementService {

    Flux<Movement> getAllMovement();

    Mono<Movement> getOneMovement(Long id);

    Mono<Movement> saveMovement(MovementDTO movementDTO);

    Mono<Movement> updateMovement(Long id ,MovementDTO movementDTO);

    Mono<Void> deleteMovement(Long id);


}

