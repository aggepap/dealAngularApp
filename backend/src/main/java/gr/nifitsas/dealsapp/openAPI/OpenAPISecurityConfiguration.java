package gr.nifitsas.dealsapp.openAPI;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
  info =@Info(
    title = "Deals Site API",
    version = "1.0",
    contact = @Contact(
      name = "Aggepap", email = "aggepap@gmail.com", url = "https://github.com/aggepap"
    ),
    license = @License(
      name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
    ),
    description = "Api Documentation for Deals App"
  ),
  security = {

  }

)
public class OpenAPISecurityConfiguration {}


