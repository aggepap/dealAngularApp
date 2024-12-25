package gr.nifitsas.dealsapp.dto.StoreDTOs;

import gr.nifitsas.dealsapp.model.Attachment;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class StoreInsertDTO {

  @NotEmpty(message = "Store name cannot be empty")
  @Size(min=3, max=40, message = "Store name must be between 3 and 40 characters")
  private String name;
  @NotEmpty(message = "Store URL cannot be empty")
  @URL(message = "This field accepts only URLs e.x. https://www.example.com")
  private String siteURL;


}
