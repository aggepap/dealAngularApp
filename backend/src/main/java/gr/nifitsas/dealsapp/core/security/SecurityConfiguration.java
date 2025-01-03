package gr.nifitsas.dealsapp.core.security;

import gr.nifitsas.dealsapp.core.authentication.JwtAuthFilter;
import gr.nifitsas.dealsapp.core.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthFilter jwtAuthFilter;
  private final UserDetailsService userDetailsService;

  /**
   * Configures the Spring Security filter chain.
   *
   * @param http The HttpSecurity object used to configure security.
   * @param jwtAuthFilter The JWT authentication filter.
   * @return The SecurityFilterChain object representing the configured security chain.
   * @throws Exception If there's an error configuring security.
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
    http
      .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer
        .configurationSource(corsConfigurationSource()))
      .csrf(AbstractHttpConfigurer::disable)
      .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(myAuthenticationEntryPoint()))
      .exceptionHandling(exceptions -> exceptions.accessDeniedHandler(myAccessDeniedHandler()))
      .authorizeHttpRequests(req -> req
        .requestMatchers("/auth/login").permitAll()
        .requestMatchers("/products/update/**").hasAnyAuthority(Role.ADMIN.name())
        .requestMatchers("/category/update/**").hasAnyAuthority(Role.ADMIN.name())
        .requestMatchers("/stores/update/**").hasAnyAuthority(Role.ADMIN.name())
        .requestMatchers("/category/remove/**").hasAnyAuthority(Role.ADMIN.name())
        .requestMatchers("/stores/remove/**").hasAnyAuthority(Role.ADMIN.name())
        .requestMatchers("/users/remove/**").hasAnyAuthority(Role.ADMIN.name())
        .requestMatchers("/products/add/**").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
        .requestMatchers("/stores/add/**").hasAnyAuthority(Role.ADMIN.name())
        .requestMatchers("/category/add/**").hasAnyAuthority(Role.ADMIN.name())
        .requestMatchers("/**").permitAll()
      ).sessionManagement((session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
      .authenticationProvider(authenticationProvider())
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  /**
   * Creates a CorsConfigurationSource for enabling CORS.
   *
   * @return A CorsConfigurationSource object.
   */
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowCredentials(true);
    configuration.setAllowedOrigins(List.of("http://localhost:4200"));
    configuration.setAllowedMethods(List.of("*"));
    configuration.setAllowedHeaders(List.of("*"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  /**
   * Creates a DaoAuthenticationProvider bean for user authentication.
   *
   * @return A DaoAuthenticationProvider object.
   */
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }
  /**
   * Creates a BCryptPasswordEncoder bean for password hashing.
   *
   * @return A BCryptPasswordEncoder object with a strength of 11.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(11);
  }

  /**
   * Retrieves the AuthenticationManager from the AuthenticationConfiguration.
   *
   * @param conf The AuthenticationConfiguration object.
   * @return The AuthenticationManager object.
   * @throws Exception If there's an error getting the AuthenticationManager.
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration conf) throws Exception {
    return conf.getAuthenticationManager();
  }
  /**
   * Creates a custom AuthenticationEntryPoint for handling authentication failures.
   *
   * @return A CustomAuthEntryPoint object.
   */
  @Bean
  public AuthenticationEntryPoint myAuthenticationEntryPoint() {
    return new CustomAuthEntryPoint();
  }
  /**
   * Creates a custom AccessDeniedHandler for handling access denied exceptions.
   *
   * @return A CustomAccessDeniedHandler object.
   */
  @Bean
  public AccessDeniedHandler myAccessDeniedHandler() {
    return new CustomAccessDeniedHandler();
  }
}
