package gr.nifitsas.dealsapp.controller;

import gr.nifitsas.dealsapp.core.authentication.AuthService;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotAuthorizedException;
import gr.nifitsas.dealsapp.core.exceptions.ValidationException;
import gr.nifitsas.dealsapp.dto.AuthRequestDTO;
import gr.nifitsas.dealsapp.dto.AuthResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestController {
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthRestController.class);
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthRequestDTO authRequest) throws AppObjectNotAuthorizedException {
    try {
      AuthResponseDTO authResponseDTO = authService.authenticate(authRequest);
      LOGGER.info("User Authenticated: {}", authResponseDTO);
      return ResponseEntity.ok(authResponseDTO);
    }catch (AppObjectNotAuthorizedException e){
      LOGGER.error("ERROR: Could not find User.", e);
      throw e;
    }


  }

}
