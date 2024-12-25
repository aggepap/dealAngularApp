package gr.nifitsas.dealsapp.dto.categoryDTOs;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInsertDTO {

  @NotNull(message = "Category's name cannot be empty")
  private String name;

  @NotNull(message = "Category's icon cannot be empty")
  private String icon;
}
