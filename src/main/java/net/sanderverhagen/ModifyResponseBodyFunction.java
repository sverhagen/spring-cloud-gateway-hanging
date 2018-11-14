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

        // make some modifications, we're in a "modifyResponseBody" filter after all
        //((ObjectNode) jsonNode).set("someNewKey", someNewStuff());

        throw new RuntimeException("something wrong"); // imagine this exception thrown from someNewStuff()

        // return Mono.just(jsonNode);
    }
}
