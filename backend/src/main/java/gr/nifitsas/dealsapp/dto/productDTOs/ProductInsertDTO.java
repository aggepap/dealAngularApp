package gr.nifitsas.dealsapp.dto.productDTOs;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ProductInsertDTO {


  @NotNull(message = "Product's name cannot be empty")
  private String name;
  private String description;
  private String coupon;
  @NotNull(message = "Product's url cannot be empty")
  private String url;
  private double price;

}
