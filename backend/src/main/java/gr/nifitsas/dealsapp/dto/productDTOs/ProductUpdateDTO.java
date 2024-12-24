package gr.nifitsas.dealsapp.dto.productDTOs;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ProductUpdateDTO {


  private String name;
  private String description;
  private String coupon;
  private String url;
  private Double price;

}
