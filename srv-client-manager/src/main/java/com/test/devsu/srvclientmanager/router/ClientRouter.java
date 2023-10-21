package com.test.devsu.srvclientmanager.router;

import com.test.devsu.srvclientmanager.handler.ClientHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ClientRouter {

    private static final String PATH = "/clientes";

    @Bean
    public RouterFunction<ServerResponse> router(ClientHandler handler){
        return RouterFunctions.route()
                .GET(PATH,handler::getAllClients)
                .GET(PATH+"/{id}/reportes",handler::getClientAccountReport)
                .GET(PATH+"/{id}", handler::getOneClient)
                .POST(PATH, handler::saveClient)
                .PUT(PATH+"/{id}", handler::updateClient)
                .DELETE(PATH+"/{id}", handler::deleteClient)
                .build();
    }
}
