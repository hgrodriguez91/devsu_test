package com.test.devsu.srvaccountmanager.service.impl;

import com.test.devsu.srvaccountmanager.dto.MovementDTO;
import com.test.devsu.srvaccountmanager.exception.CustomException;
import com.test.devsu.srvaccountmanager.model.Account;
import com.test.devsu.srvaccountmanager.model.Movement;
import com.test.devsu.srvaccountmanager.repository.MovementRepository;
import com.test.devsu.srvaccountmanager.service.AccountService;
import com.test.devsu.srvaccountmanager.service.MovementService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {

    private static final String NOT_FOUND_MSG = "Movement not found";
    private static final String INSUFFICIENT_BALANCE_MSG = "Insufficient funds";
    private static final String RELATED_ACCOUNT_MSG = "Related account doesn't exist";

    private final MovementRepository movementRepository;
    private final AccountService accountService;
    private final ModelMapper modelMapper;

    @Override
    public Flux<Movement> getAllMovement() {
        return Flux.fromIterable(movementRepository.findAll());
    }

    @Override
    public Mono<Movement> getOneMovement(Long id) {
        Optional<Movement> movementOptional = movementRepository.findById(id);
        return movementOptional.map(Mono::just)
                .orElseGet(() -> Mono.error(new CustomException(HttpStatus.NOT_FOUND, NOT_FOUND_MSG)));
    }

    @Override
    public Mono<Movement> saveMovement(MovementDTO movementDTO) {
        Movement mov = modelMapper.map(movementDTO, Movement.class);
        Optional<Movement> balanceOpt = movementRepository.findAccountBalance(movementDTO.getAccountId());
        Account account = accountService.getOneAccount(mov.getAccountId()).block();
        if (account == null)
            return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, RELATED_ACCOUNT_MSG));
        mov.setType(account.getType());
        if (!balanceOpt.isPresent()) {
            if ((account.getBalance().add(movementDTO.getValue()).compareTo(BigDecimal.ZERO) < 0))
                return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, INSUFFICIENT_BALANCE_MSG));
            mov.setBalance(account.getBalance().add(movementDTO.getValue()));
        } else {
            BigDecimal balance = balanceOpt.get().getBalance();
            if (balance.add(movementDTO.getValue()).compareTo(BigDecimal.ZERO) < 0) {
                return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, INSUFFICIENT_BALANCE_MSG));
            }
            mov.setBalance(balance.add(movementDTO.getValue()));
        }
        mov.setDate(Timestamp.from(Instant.now()));
        return Mono.just(movementRepository.save(mov));
    }

    /* Obs: Solo se puede modificar el ultimo movimiento, en caso de error en el balance
             crear un nuevo movimiento corrigiendo el mismo */
    @Override
    public Mono<Movement> updateMovement(Long accountId, MovementDTO movementDTO) {
        Optional<Movement> movementOptional = movementRepository.findAccountBalance(accountId);
        if (!movementOptional.isPresent())
            return Mono.error(new CustomException(HttpStatus.NOT_FOUND, NOT_FOUND_MSG));
        Movement mov = movementOptional.get();
        if (mov.getBalance().add(mov.getValue()).compareTo(movementDTO.getValue()) < 0) {
            return Mono.error(new CustomException(HttpStatus.NOT_FOUND, INSUFFICIENT_BALANCE_MSG));
        }
        BigDecimal balanceResult = mov.getBalance().add(mov.getValue()).add(movementDTO.getValue());
        mov.setBalance(balanceResult);
        mov.setDate(Timestamp.from(Instant.now()));
        mov.setValue(movementDTO.getValue());
        return Mono.just(movementRepository.save(mov));
    }

    /* Obs: Solo se puede eliminar el ultimo movimiento para mantener la integridad del historial de movimientos */
    @Override
    public Mono<Void> deleteMovement(Long accountId) {
        Optional<Movement> movementOptional = movementRepository.findAccountBalance(accountId);
        if (!movementOptional.isPresent())
            return Mono.error(new CustomException(HttpStatus.NOT_FOUND, NOT_FOUND_MSG));
        return movementOptional.<Mono<Void>>map(account -> Mono.create(m -> {
            movementRepository.delete(account);
            m.success();
        })).orElseGet(() -> Mono.error(new CustomException(HttpStatus.NOT_FOUND, NOT_FOUND_MSG)));
    }
}
