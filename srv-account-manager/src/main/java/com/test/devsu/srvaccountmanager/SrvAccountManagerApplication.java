package com.test.devsu.srvaccountmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableTransactionManagement
public class SrvAccountManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SrvAccountManagerApplication.class, args);
	}

}
