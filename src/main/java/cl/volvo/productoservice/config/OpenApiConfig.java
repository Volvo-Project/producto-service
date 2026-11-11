package cl.volvo.productoservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI productoOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Volvo - Microservicio de Productos")
                        .version("1.0.0")
                        .description("API REST para gestionar los productos (juegos) y sus categorías de la tienda Volvo"))
                .servers(List.of(
                        new Server().url("http://localhost:8082").description("Servidor local")
                ));
    }
}