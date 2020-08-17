package com.meritamerica.fullstack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FullstackApplication {

	public static void main(String[] args) {
		SpringApplication.run(FullstackApplication.class, args);
	}

}
//For Postman verification and JSON request, we need Content-type -> application/json
// Authorization -> Bearer JWT
//Server for Database is 3307 called test, while Apache is on 8080 Tomcat is on 9090. No issues