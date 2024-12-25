package gr.nifitsas.dealsapp.core.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;


public class CustomAccessDeniedHandler implements AccessDeniedHandler {

/**
 * Handles access denied exceptions.
 *
 * <p>This method is called when a user attempts to access a resource that they are not authorized to access.
 * It sets the HTTP status code to 403 (Forbidden), sets the content type to JSON,
 * and writes a JSON response indicating that the user is not authorized.
 *
 * @param request The HTTP request object.
 * @param response The HTTP response object.
 * @param accessDeniedException The AccessDeniedException that occurred.
 * @throws IOException If an I/O error occurs while writing the response.
 * @throws ServletException If a servlet-specific error occurs.
 */
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType("application/json;charset=UTF-8");

    String json = "{\"code\": \"userNotAuthorized\", \"description\": \"User is not allowed to access this endpoint.\"}";
    response.getWriter().write(json);
  }
}
