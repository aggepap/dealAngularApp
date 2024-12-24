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
        .requestMatchers("/users").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
        .requestMatchers("/products/all").permitAll()
        .requestMatchers("/products/results").permitAll()
        .requestMatchers("/products/find").permitAll()
        .requestMatchers("/products/update/**").hasAnyAuthority(Role.ADMIN.name())
        .requestMatchers("/products/remove/**").hasAnyAuthority(Role.ADMIN.name())
        .requestMatchers("/products/add/**").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
        .requestMatchers("/**").permitAll()
      ).sessionManagement((session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
      .authenticationProvider(authenticationProvider())
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

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

  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(11);
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration conf) throws Exception {
    return conf.getAuthenticationManager();
  }

  @Bean
  public AuthenticationEntryPoint myAuthenticationEntryPoint() {
    return new CustomAuthEntryPoint();
  }
  @Bean
  public AccessDeniedHandler myAccessDeniedHandler() {
    return new CustomAccessDeniedHandler();
  }
}
