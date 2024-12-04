package gr.nifitsas.dealsapp.dto;


import gr.nifitsas.dealsapp.model.Attachment;
import gr.nifitsas.dealsapp.model.static_data.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductReadOnlyDTO {

  private Long id;
  private String sku;
  private String name;
  private Category category;
  private Attachment image;
  private Double lowestPrice;


}
