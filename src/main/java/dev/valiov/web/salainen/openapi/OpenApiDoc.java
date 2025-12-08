package dev.valiov.web.salainen.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Salainen Jouluystävä API",
                description = "API for Salainen Jouluystävä",
                version = "0.0.1"
        ),
        servers = {
                @Server(url = "http://localhost:9000/", description = "Development environment"),
                @Server(url = "https://stag-api.salainen.valiov.dev/dev", description = "Staging server"),
                @Server(url = "https://prod-api.salainen.valiov.dev/v0", description = "Default production server")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class OpenApiDoc {
}
