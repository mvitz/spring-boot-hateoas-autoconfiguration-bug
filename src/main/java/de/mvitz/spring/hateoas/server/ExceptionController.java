package de.mvitz.spring.hateoas.server;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionController {

  @GetMapping("/exception")
  public String throwException() {
    throw new RuntimeException("Foo");
  }

  @ExceptionHandler
  public ResponseEntity<ExceptionResource> handleException(RuntimeException e) {
    System.out.println("ExceptionController.handleException");
    return ResponseEntity.badRequest().body(new ExceptionResource(e));
  }

  static class ExceptionResource extends ResourceSupport {
    @JsonProperty("message")
    String message;
    public ExceptionResource(Exception e) {
      this.message = e.getMessage();
      add(new Link("http://google.de", Link.REL_SELF));
    }
  }
}
