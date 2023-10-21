package com.test.devsu.srvaccountmanager.handler;

import com.test.devsu.srvaccountmanager.model.Account;
import com.test.devsu.srvaccountmanager.service.ReporteService;
import com.test.devsu.srvaccountmanager.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class ReportHandler {

    public final ReporteService reporteService;

    public Mono<ServerResponse> getReport(ServerRequest request)  {
        Long clientId = Long.valueOf(request.pathVariable("clientId"));
        MultiValueMap<String, String> queryParams = request.queryParams();
        Timestamp initDate = Util.getDateFromQueryParams(queryParams.get("fechaini").get(0));
        Timestamp finalDate = Util.getDateFromQueryParams(queryParams.get("fechaend").get(0));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(reporteService.genarateReport(clientId, initDate, finalDate), Account.class);
    }


}
