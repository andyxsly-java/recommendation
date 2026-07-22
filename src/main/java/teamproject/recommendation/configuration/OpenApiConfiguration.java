package teamproject.recommendation.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI recommendationOpenApi() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Recommendation Service API")
                                .version("1.0")
                                .description("REST API сервиса банковских рекомендаций.")
                                .contact(
                                        new Contact()
                                                .name("Team Project")
                                )
                )
                .externalDocs(
                        new ExternalDocumentation()
                                .description("Project Documentation")
                );
    }
}
