package gr.nifitsas.dealsapp.dto.UserDTOs;

import gr.nifitsas.dealsapp.core.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserReadOnlyDTO {
  private String uuid;
  private String username;
  private Role role;
}
