package gr.nifitsas.dealsapp.dto.StoreDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class StoreUpdateDTO {
  private String name;
  private String siteURL;

}
