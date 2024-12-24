package gr.nifitsas.dealsapp.core.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;

public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    String json = "{\"code\": \"userNotAuthenticated\", \"message\": \"User must be authenticated to access this resource\"}";
    response.setContentType("application/json,charset=utf-8");
    response.getWriter().write(json);

  }
}
