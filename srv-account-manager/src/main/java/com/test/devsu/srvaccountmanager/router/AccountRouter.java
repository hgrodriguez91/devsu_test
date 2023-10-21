package com.test.devsu.srvaccountmanager.router;

import com.test.devsu.srvaccountmanager.handler.AccountHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class AccountRouter {

    private static final String PATH = "/cuentas";

    @Bean
    public RouterFunction<ServerResponse> accountRoutes(AccountHandler handler){
        return RouterFunctions.route()
                .GET(PATH,handler::getAllAccounts)
                .GET(PATH+"/{id}", handler::getOneAccount)
                .POST(PATH, handler::createAccount)
                .PUT(PATH+"/{id}", handler::updateAccount)
                .DELETE(PATH+"/{id}", handler::deleteAccount)
                .build();
    }

}
