package nl.jafeth.van.elten.exilian.winter.creative.competition2023.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("nl.jafeth.van.elten.exilian.winter.creative.competition2023.common.db.entity")
@EnableJpaRepositories("nl.jafeth.van.elten.exilian.winter.creative.competition2023.backend.repository")
@EnableAutoConfiguration
public class AnimalBackendApplication {
    public static void main(String... args) {
        SpringApplication.run(AnimalBackendApplication.class, args);
    }
}
