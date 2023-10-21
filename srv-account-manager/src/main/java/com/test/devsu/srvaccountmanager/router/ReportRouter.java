package com.test.devsu.srvaccountmanager.router;

import com.test.devsu.srvaccountmanager.handler.ReportHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ReportRouter {
    private static final String PATH = "/acccount-status";

    @Bean
    public RouterFunction<ServerResponse> repoRoutes(ReportHandler handler) {
        return RouterFunctions.route()
                .GET(PATH + "/{clientId}", handler::getReport)
                .build();
    }
}
