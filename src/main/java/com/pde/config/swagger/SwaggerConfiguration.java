package com.pde.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "PDE Client APIs",
                version = "0.0.1",
                description = "API Documentation of the PDE Client."
        )
)
public class SwaggerConfiguration {
}
