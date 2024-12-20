package gr.nifitsas.dealsapp.dto.productDTOs;


import gr.nifitsas.dealsapp.model.Attachment;
import gr.nifitsas.dealsapp.model.static_data.Category;
import gr.nifitsas.dealsapp.model.static_data.Store;
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
  private String name;
  private String description;
  private Category category;
  private Store store;
  private String coupon;
  private String url;
  private Double price;
  private Attachment image;
  private Double lowestPrice;


}
