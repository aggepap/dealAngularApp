package gr.nifitsas.dealsapp.dto.productDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ProductFilterPaginatedDTO {
  private int page;
  private int size;
  private String filter;
}
