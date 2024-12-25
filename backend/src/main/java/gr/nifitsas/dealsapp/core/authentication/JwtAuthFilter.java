package gr.nifitsas.dealsapp.core.authentication;

import gr.nifitsas.dealsapp.core.security.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
  private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthFilter.class);
  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;


  /**
   * Filters incoming HTTP requests and performs JWT token authentication.
   *
   * @param request The HTTP request object.
   * @param response The HTTP response object.
   * @param filterChain The filter chain for further processing.
   * @throws ServletException If a servlet-specific error occurs.
   * @throws IOException If an I/O error occurs.
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
  String authHeader = request.getHeader("Authorization");
  String jwt;
  String username;
  String userRole;

    // Check for Authorization header and valid JWT format
  if (authHeader == null || !authHeader.startsWith("Bearer ")) {
    filterChain.doFilter(request, response);
    return;
  }
    jwt = authHeader.substring(7);

    // Attempt to extract username and role from the JWT
  try {
    username = jwtService.extractSubject(jwt);
    userRole = jwtService.getStringClaim(jwt, "role");

    // Authenticate user if necessary
    if(username != null && userRole != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      if(jwtService.isTokenValid(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }else {
        LOGGER.warn("Invalid token for user: {} ", username);
      }
    }
  }catch (ExpiredJwtException e){
LOGGER.warn("Warn: Expired JWT token", e);
response.setStatus(HttpStatus.UNAUTHORIZED.value());
response.setContentType("application/json");
response.getWriter().write(e.getMessage());
return;
  }catch (Exception e) {
    LOGGER.warn("Warn: Something went wrong on JWT parsing", e);
    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType("application/json");
    response.getWriter().write(e.getMessage());
    return;
  }
    // Proceed with the filter chain if authentication successful
  filterChain.doFilter(request, response);
  }
}
