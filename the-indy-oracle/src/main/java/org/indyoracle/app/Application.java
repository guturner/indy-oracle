package org.indyoracle.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Entry point into application. Running this will launch an embedded server.
 * 
 * @author Guy
 *
 */

@SpringBootApplication
@ComponentScan(basePackages = { "org.indyoracle.controllers",
		"org.indyoracle.cron", "org.indyoracle.interceptors.config",
		"org.indyoracle.security.config" })
@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}