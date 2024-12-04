package gr.nifitsas.dealsapp.dto;


import gr.nifitsas.dealsapp.model.Attachment;
import gr.nifitsas.dealsapp.model.static_data.Category;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ProductInsertDTO {

  @NotNull(message = "Product's name cannot be empty")
  private String name;
  @Column(unique = true)
  private String sku;

}
