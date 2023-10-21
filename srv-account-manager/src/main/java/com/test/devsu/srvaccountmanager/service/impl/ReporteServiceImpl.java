package com.test.devsu.srvaccountmanager.service.impl;

import com.test.devsu.srvaccountmanager.dto.ReportResponseDTO;
import com.test.devsu.srvaccountmanager.model.Account;
import com.test.devsu.srvaccountmanager.repository.AccountRepository;
import com.test.devsu.srvaccountmanager.repository.MovementRepository;
import com.test.devsu.srvaccountmanager.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public Mono<ReportResponseDTO> genarateReport(Long clientId, Timestamp initial, Timestamp finalDate) {
        List<Account> clientAccounts = accountRepository.findAccountByClientId(clientId);
        return Mono.just(ReportResponseDTO.builder().accountDetails(clientAccounts).build());
    }
}
