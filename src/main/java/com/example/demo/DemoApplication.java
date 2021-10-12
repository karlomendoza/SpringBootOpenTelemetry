package com.example.demo;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.autoconfigure.OpenTelemetrySdkAutoConfiguration;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

  @Autowired
  private UserRepository userRepository;

  public static Tracer tracer;

  public static void main(String[] args) throws InterruptedException {
    SpringApplication.run(DemoApplication.class, args);

    System.setProperty("otel.traces.exporter", "otlp");
    System.setProperty("otel.metrics.exporter", "none");
    System.setProperty("otel.exporter.otlp.endpoint", "http://otel-agent.karlo:4317");
    System.setProperty("otel.resource.attributes", "service.name=OtlpKarloTestv2");

    OpenTelemetry openTelemetry = OpenTelemetrySdkAutoConfiguration.initialize();

    tracer = openTelemetry.getTracer("karlo-test");

    tracer.spanBuilder("asdas").startSpan().end();

    int i = 0;
    while (true ) {
      i++;
      Thread.sleep(5);
      Span parentSpan = tracer.spanBuilder("parent").startSpan();
      parentSpan.setAttribute("ID", i);
      parentSpan.setAttribute("KARLO", "TEST");
      System.out.println("current ID: " + i);
      try (Scope scope = parentSpan.makeCurrent()) {
        //DO actual work
      } finally {
        parentSpan.end();
      }
    }

  }

  @PostConstruct
  private void initDb() {
    User user = new User();
    user.setUserType("STUDENT");
    user.setUserName("PeterM");
    user.setPassword("ABC123abc*");
    user.setDateofBirth(new Date());
    user.setCreationTime(new Date());
    userRepository.save(user);
  }
}
