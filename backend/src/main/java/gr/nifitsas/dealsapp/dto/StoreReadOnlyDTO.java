package gr.nifitsas.dealsapp.dto;

import gr.nifitsas.dealsapp.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreReadOnlyDTO {
  private Long id;
  private String name;
  private String siteURL;
  private String logoURL;
  private Set<Product> products;
}
