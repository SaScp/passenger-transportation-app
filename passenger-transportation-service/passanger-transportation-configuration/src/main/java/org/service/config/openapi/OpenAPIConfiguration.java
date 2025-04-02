package org.service.config.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@OpenAPIDefinition(
        info = @Info(
                title = "Passenger API",
                description = "API для маркетплейса пассажирских перевозок", version = "1.0.0",
                contact = @Contact(
                        name = "Alex",
                        url = "https://github.com/SaScp"
                )
        )
)
public class OpenAPIConfiguration { }
