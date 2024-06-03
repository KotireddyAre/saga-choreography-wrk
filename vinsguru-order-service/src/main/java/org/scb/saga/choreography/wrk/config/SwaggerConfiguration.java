package org.scb.saga.choreography.wrk.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi api() {
        String[] paths = { "/orders/**" };
        String[] packagedToMatch = { "org.scb.saga.choreography.wrk.controller" };

        return GroupedOpenApi.builder()
                .group("orders")
                .pathsToMatch(paths).packagesToScan(packagedToMatch)
                .addOpenApiCustomizer(
                        openApi -> openApi.info(
                                new Info()
                                        .title("Order Service API Specification")
                                        .version("3.0.0")
                                        .description("Documentation for Order Service API v1.0"))
                                .setServers(
                                        List.of(
                                                new Server().url("http://localhost:8081").description("DEV")))
                )
                .build();
    }
}
