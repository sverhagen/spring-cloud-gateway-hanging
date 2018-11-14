# Spring Cloud Gateway Hanging

Little project to demonstrate how Spring Cloud Gateway is hanging when an exception is forced during test execution,
because `Content-Length` is already set to a larger value, presumably the length of the original payload, making
RestAssured wait and never finish.

## Usage

Run:

    $ ./mvnw test

Expected outcomes:

- Unchanged code:

  **Test hangs**

- Uncomment the following line in `ModifyResponseBodyFunction`:

      exchange.getResponse().getHeaders().remove("Content-Length");
      
  **Test finished nicely**

Run the application:

    $ ./mvnw spring-boot:run

You can use a `curl` to the same effect. You need some kind of server to forward to, as set in the `baseUrl`
configuration property (see `application.properties`).
