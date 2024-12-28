package gr.nifitsas.dealsapp.dto.productDTOs;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ProductUpdateDTO {


  @NotNull(message = "Product's name cannot be empty")
  private String name;
  private String description;
  private String coupon;
  @NotNull(message = "Product's url cannot be empty")
  @URL(message = "Product's URL field accepts only URLs e.x. https://www.example.com")
  private String url;
  private Double price;

}
