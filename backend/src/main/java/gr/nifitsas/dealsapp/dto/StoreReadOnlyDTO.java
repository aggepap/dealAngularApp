package gr.nifitsas.dealsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreReadOnlyDTO {
  private Long id;
  private String name;
  private String siteURL;
  private String logoURL;
}
