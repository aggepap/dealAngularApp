package gr.nifitsas.dealsapp.dto;


import gr.nifitsas.dealsapp.model.static_data.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRealOnlyDTO {

  private Long id;
  private String sku;
  private String name;
  private Category category;
  private String imgURL;
  private Double lowestPrice;


}
