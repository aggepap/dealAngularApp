package gr.nifitsas.dealsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO {
  private String code;
  private String description;

  public ResponseDTO(String code) {
    this.code = code;
    this.description = "";
  }
}
