package de.mvitz.spring.hateoas.server;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;

@SpringBootApplication
//@EnableHypermediaSupport(type = HAL)
public class SpringHateoasApplication {

  static class User {
    String name;
    User(String name) { this.name = name; }
  }

  static class UserResource extends ResourceSupport {
    @JsonProperty("name")
    String name;
    public UserResource(User user) { this.name = user.name; }
  }

  static class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResource> {
    public UserResourceAssembler() {
      super(UserController.class, UserResource.class);
    }

    @Override
    protected UserResource instantiateResource(User entity) { return new UserResource(entity); }

    @Override
    public UserResource toResource(User entity) { return createResourceWithId(entity.name, entity); }
  }

  @Controller
  @RequestMapping("/users")
  @ResponseBody
  @ExposesResourceFor(UserResource.class)
  class UserController {
    UserResourceAssembler userResourceAssembler = new UserResourceAssembler();

    @RequestMapping
    public ResponseEntity<Resources<UserResource>> users() {
      final List<User> users = Collections.singletonList(new User("foo"));
      final List<UserResource> representations =
          users.stream().map(userResourceAssembler::toResource).collect(toList());
      return ResponseEntity.ok().body(new Resources<>(representations));
    }

    @RequestMapping("/{name}")
    public ResponseEntity<UserResource> user(@PathVariable("name") String name) {
      return ResponseEntity.ok().body(userResourceAssembler.toResource(new User(name)));
    }
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringHateoasApplication.class, args);
  }
}
