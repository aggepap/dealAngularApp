package gr.nifitsas.dealsapp.controller;

import gr.nifitsas.dealsapp.core.authentication.AuthService;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotAuthorizedException;
import gr.nifitsas.dealsapp.dto.AuthRequestDTO;
import gr.nifitsas.dealsapp.dto.AuthResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

  @Operation(summary = "Logins user with correct credentials")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "User Logged in succesfully",
      content = @Content),
    @ApiResponse(responseCode = "401", description = "Invalid email or password",
      content = @Content) })
  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthRequestDTO authRequest) throws AppObjectNotAuthorizedException {
    try {
      AuthResponseDTO authResponseDTO = authService.authenticate(authRequest);
      LOGGER.info("User Authenticated: {}", authResponseDTO);
      return ResponseEntity.ok(authResponseDTO);
    }catch (AppObjectNotAuthorizedException  e){
      LOGGER.error("ERROR: Could not find User.", e);
      throw e;
    }


  }

}
