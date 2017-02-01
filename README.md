# Spring Boot HATEOAS AutoConfiguration Bug

Small project to show a possible bug.

After starting the application with

```
mvn spring-boot:run
```

the applcation responds with:

```
curl -i localhost:8080/exception -HAccept:application/hal+json
HTTP/1.1 200
Content-Type: application/hal+json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 01 Feb 2017 06:23:45 GMT

{"links":[{"rel":"self","href":"http://google.de"}],"message":"Foo"}
```

This is not valid HAL!

But if `@EnableHypermediaSupport(type = HAL)` is applied in `src/main/java/de/mvitz/spring/hateoas/server/SpringHateoasApplication.java`
(remove comment in line 20) the application responds correctly with:

```
curl -i localhost:8080/exception -HAccept:application/hal+json
HTTP/1.1 400
Content-Type: application/hal+json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 01 Feb 2017 06:29:51 GMT
Connection: close

{"_links":{"self":{"href":"http://google.de"}},"message":"Foo"}
```
