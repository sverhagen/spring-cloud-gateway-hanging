package net.sanderverhagen;

import com.xebialabs.restito.server.StubServer;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.contentType;
import static com.xebialabs.restito.semantics.Action.ok;
import static com.xebialabs.restito.semantics.Action.resourceContent;
import static com.xebialabs.restito.semantics.Condition.get;
import static io.restassured.RestAssured.with;
import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.main.banner-mode=off")
public class ModifyResponseBodyIntegrationTest {
    private static StubServer server = new StubServer();

    @LocalServerPort
    private Integer serverPort;

    @BeforeClass
    public static void setUpClass() {
        server.start();
        System.setProperty("baseUrl", format("http://localhost:%d", server.getPort()));
    }

    @Before
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = serverPort;
    }

    @Test
    public void testIncludedGetsException() {
        whenHttp(server)
                .match(get("/api1/blah/blah"))
                .then(ok(), contentType("application/json"), resourceContent("arrayResponse.json"));

        with().expect().statusCode(500)
                .and().body("path", equalTo("/api1/blah/blah"))
                .and().body("status", equalTo(500))
                .and().body("error", equalTo("Internal Server Error"))
                .and().body("message", equalTo("something wrong"))
                .when().get("/api1/blah/blah");
    }
}
