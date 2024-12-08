package edu.utp.pe.Test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = "edu.utp.pe")
@EntityScan(basePackages = "edu.utp.pe.Entity")
@EnableJpaRepositories("edu.utp.pe.Repository")
@ComponentScan(basePackages = {"edu.utp.pe"})
public class SisEssaludApplication {

	public static void main(String[] args) {
		SpringApplication.run(SisEssaludApplication.class, args);
	}

}
