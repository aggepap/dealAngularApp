package gr.nifitsas.dealsapp.core.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;

public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

  /**
   * Handles authentication exceptions.
   *
   * <p>This method is called when authentication fails. It sets the HTTP status code
   * to 401 (Unauthorized), sets the content type to JSON, and writes a JSON response
   * indicating that the user is not authenticated.
   *
   * @param request The HTTP request object.
   * @param response The HTTP response object.
   * @param authException The AuthenticationException that occurred.
   * @throws IOException If an I/O error occurs while writing the response.
   * @throws ServletException If a servlet-specific error occurs.
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    String json = "{\"code\": \"userNotAuthenticated\", \"message\": \"User must be authenticated to access this resource\"}";
    response.setContentType("application/json,charset=utf-8");
    response.getWriter().write(json);

  }
}
