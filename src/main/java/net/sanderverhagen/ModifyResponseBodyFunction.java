package net.sanderverhagen;

import com.fasterxml.jackson.databind.JsonNode;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class ModifyResponseBodyFunction {
    public Publisher<JsonNode> modifyBody(ServerWebExchange exchange, JsonNode jsonNode) {
        // during test execution, if expecting an exception, Content-Length is already set to a larger value,
        // presumably the length of the original payload,
        // making RestAssured wait and never finish; it's weird
//        exchange.getResponse().getHeaders().remove("Content-Length");

        throw new RuntimeException("something wrong");
    }
}
