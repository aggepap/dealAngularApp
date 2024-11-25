package gr.nifitsas.dealsapp.dto.categoryDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdateDTO {
  private Long id;
  private String name;
  private String icon;
}
