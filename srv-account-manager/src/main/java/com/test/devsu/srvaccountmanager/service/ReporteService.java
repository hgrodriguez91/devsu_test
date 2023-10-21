package com.test.devsu.srvaccountmanager.service;

import com.test.devsu.srvaccountmanager.dto.ReportResponseDTO;
import com.test.devsu.srvaccountmanager.model.Account;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

public interface ReporteService {

    Mono<ReportResponseDTO> genarateReport(Long clientId, Timestamp initial, Timestamp finalDate);
}
