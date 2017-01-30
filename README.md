# Spring Boot HATEOAS AutoConfiguration Bug

Small project to show a possible bug.

After starting the application with

```
mvn spring-boot:run
```

the applcation responds with:

```
curl -i localhost:8080/users -HAccept:application/hal+json
HTTP/1.1 200
X-Application-Context: application
Content-Type: application/hal+json;charset=UTF-8
Transfer-Encoding: chunked
Date: Mon, 30 Jan 2017 12:52:21 GMT

[{"links":[{"rel":"self","href":"http://localhost:8080/users/foo"}]}]
```

This is not valid HAL!

But if `@EnableHypermediaSupport(type = HAL)` is applied in `src/main/java/de/mvitz/spring/hateoas/server/SpringHateoasApplication.java`
(remove comment in line 20) the application responds correctly with:

```
curl -i localhost:8080/users -HAccept:application/hal+json
HTTP/1.1 200
X-Application-Context: application
Content-Type: application/hal+json;charset=UTF-8
Transfer-Encoding: chunked
Date: Mon, 30 Jan 2017 12:55:03 GMT

[{"_links":{"self":{"href":"http://localhost:8080/users/foo"}}}]
```
