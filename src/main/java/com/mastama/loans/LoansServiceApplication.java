package com.mastama.loans;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title = "Loans microservices REST API Documentation",
                description = "BANKAPAYA Loans microservices REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Singgih Pratama",
                        email = "mastama@modernitpooling.co.id",
                        url = "https://github.com/mastama?tab=repositories"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://github.com/mastama?tab=repositories"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "BANKAPAYA Loans microservices REST API Documentation",
                url = "https://github.com/mastama?tab=repositories"
        )
)
public class LoansServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoansServiceApplication.class, args);
    }

}
