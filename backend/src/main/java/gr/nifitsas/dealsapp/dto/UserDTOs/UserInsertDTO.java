package gr.nifitsas.dealsapp.dto.UserDTOs;

import gr.nifitsas.dealsapp.core.enums.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInsertDTO {


  @NotEmpty(message = "Username cannot be empty, and must be a email")
  @Email(message = "Invalid Username.Please use your email")
  @Size(min=3, max=40, message = "Username must be between 3 and 40 characters")
  private String username;
  @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[@#$!%&*]).{8,}$", message = "Invalid password")
  private String password;
}
