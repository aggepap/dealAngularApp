package gr.nifitsas.dealsapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class StoreInsertDTO {

  @NotNull(message = "Store's name cannot be empty")
  private String name;
  private String siteURL;
  private String logoURL;

}
