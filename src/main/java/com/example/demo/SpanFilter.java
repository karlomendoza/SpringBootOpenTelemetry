package com.example.demo;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.context.Scope;
import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class SpanFilter implements Filter {

  //This filter catches all calls and gets the headers and adds all headers as span attributes.
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    
    Span span = DemoApplication.tracer.spanBuilder("SpanFilter").startSpan();
    try (Scope scope = span.makeCurrent()) {
      HttpServletRequest httpRequest = (HttpServletRequest) request;
      Enumeration<String> headerNames = httpRequest.getHeaderNames();
      if (headerNames != null) {
        while (headerNames.hasMoreElements()) {
          String s = headerNames.nextElement();
          span.setAttribute(s, httpRequest.getHeader(s));
        }
      }
    } catch (Throwable t) {
      span.setStatus(StatusCode.ERROR, "Change it to your error message");
    } finally {
      span.end(); // closing the scope does not end the span, this has to be done manually
    }
    chain.doFilter(request, response);
  }
}