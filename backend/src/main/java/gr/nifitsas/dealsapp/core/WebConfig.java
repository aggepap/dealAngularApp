package gr.nifitsas.dealsapp.core;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Component
public class WebConfig {

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
          .allowedOrigins("http://localhost:4200/")
          .allowedHeaders("*")
          .allowedMethods("GET", "POST", "PUT","PATCH", "DELETE", "OPTIONS")
          .allowCredentials(true);
      }
    };
  }
}
