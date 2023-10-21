package com.test.devsu.srvaccountmanager.router;


import com.test.devsu.srvaccountmanager.handler.MovementHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class MovementRouter {

    private static final String PATH = "/movimientos";

    @Bean
    public RouterFunction<ServerResponse> movRoutes(MovementHandler handler) {
        return RouterFunctions.route()
                .GET(PATH, handler::getAllMovements)
                .GET(PATH + "/{id}", handler::getOneMovement)
                .POST(PATH, handler::saveMovement)
                .PUT(PATH + "/{id}", handler::updateMovement)
                .DELETE(PATH + "/{id}", handler::deleteMovement)
                .build();
    }
}
