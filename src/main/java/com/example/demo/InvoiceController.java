package com.example.demo;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.context.Scope;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class InvoiceController {
  private static final String INSTRUMENTATION_NAME = InvoiceController.class.getName();

  @GetMapping("/manualSpan")
  public String manualSpan(@RequestParam(value = "name", defaultValue = "Stranger") String name,  @RequestHeader Map<String, String> headers) {

    Span span = DemoApplication.tracer.spanBuilder("hello request").startSpan();
    // put the span into the current Context
    try (Scope scope = span.makeCurrent()) {
      // your use case
      headers.forEach((key, value) ->
      span.setAttribute(key,value));
    } catch (Throwable t) {
      span.setStatus(StatusCode.ERROR, "Change it to your error message");
    } finally {
      span.end(); // closing the scope does not end the span, this has to be done manually
    }
    return "hello " + name;
  }

  //This 2 endpoints use the SpanFilter to autoInclude the header data in the span.
  // no need to manually create a span, since the autoinstrumentation does it.
  @GetMapping("/paramAutoSpam")
  public String paramAutoSpam(@RequestParam(value = "name", defaultValue = "Stranger") String name,  @RequestHeader Map<String, String> headers) {
    return "hello " + name;
  }

  @PostMapping("/requestBodyAutoSpam")
  public String requestBodyAutoSpam(@RequestBody Integer id) {
    return "sayonara " + id;
  }

  //Database requests
  @Autowired
  private UserRepository userRepository;

  @GetMapping("/getAllUsers")
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @GetMapping("/saveUser")
  public List<User> storeUser() {
    User user = new User();
    user.setUserType("STUDENT");
    user.setUserName("PeterM" + Math.random()*10000) ;
    user.setPassword("ABC123abc*");
    user.setDateofBirth(new Date());
    user.setCreationTime(new Date());
    userRepository.save(user);
    return userRepository.findAll();
  }

  //Basic Errors
  @GetMapping("/500")
  public String a500() {
    throw new ResponseStatusException(
        HttpStatus.INTERNAL_SERVER_ERROR, "internal server error"
    );
  }

  @GetMapping("/401")
  public String a401() {
    throw new ResponseStatusException(
        HttpStatus.UNAUTHORIZED, "unauthorized"
    );
  }

  @GetMapping("/404")
  public String a404() {
    throw new ResponseStatusException(
        HttpStatus.NOT_FOUND, "not found"
    );
  }
}