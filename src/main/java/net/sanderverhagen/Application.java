package net.sanderverhagen;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    @Autowired
    private ModifyResponseBodyFunction responseBodyModifier;

    @Value("${baseUrl}")
    private String servicesBaseUrl;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/**")
                        .filters(this::filters)
                        .uri(servicesBaseUrl))
                .build();
    }

    private GatewayFilterSpec filters(GatewayFilterSpec filter) {
        return filter
                .modifyResponseBody(JsonNode.class, JsonNode.class, responseBodyModifier::modifyBody);
    }
}
