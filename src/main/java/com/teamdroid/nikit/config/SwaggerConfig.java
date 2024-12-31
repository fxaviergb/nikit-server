package com.teamdroid.nikit.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger/OpenAPI documentation.
 *
 * <p>This class configures the OpenAPI documentation for the NikIT API, including API metadata
 * such as title, version, contact information, and license details.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configures the OpenAPI documentation bean.
     *
     * <p>This method defines the metadata for the API, including its title, version, description,
     * contact details, and licensing information.
     *
     * @return An {@code OpenAPI} object containing the API documentation configuration.
     */
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("NikIT API")
                                .version("v1")
                                .description("API para la generación de conocimiento de NikIT")
                                .contact(
                                        new Contact()
                                                .name("Soporte TeamDroid")
                                                .email("teamdroidtech@gmail.com")
                                                .url("https://nikit.com"))
                                .license(
                                        new License()
                                                .name("MIT License")
                                                .url("https://opensource.org/licenses/MIT")));
    }
}
