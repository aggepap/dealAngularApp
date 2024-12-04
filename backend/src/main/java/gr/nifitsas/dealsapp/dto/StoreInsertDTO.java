package gr.nifitsas.dealsapp.dto;

import gr.nifitsas.dealsapp.model.Attachment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class StoreInsertDTO {

  private String name;
  private String siteURL;
  private String logoURL;

}
