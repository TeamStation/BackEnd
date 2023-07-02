package com.teamstation.teamstation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableJpaAuditing
@PropertySource("classpath:/env.properties")
public class TeamstationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamstationApplication.class, args);
	}

}
