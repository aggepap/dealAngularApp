package gr.nifitsas.dealsapp.openAPI;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;

@Configuration
public class OpenApiConfig {

  /**
   * This method is needed to allow sending multipart requests. For example, when an item is
   * created together with an image. If this is not set the request will return an exception with:
   * <p>
   * Resolved [org.springframework.web.HttpMediaTypeNotSupportedException: Content-Type
   * 'application/octet-stream' is not supported]
   *
   * @param converter
   */
  public OpenApiConfig(MappingJackson2HttpMessageConverter converter) {
    var supportedMediaTypes = new ArrayList<>(converter.getSupportedMediaTypes());
    supportedMediaTypes.add(new MediaType("application", "octet-stream"));
    converter.setSupportedMediaTypes(supportedMediaTypes);
  }
}

