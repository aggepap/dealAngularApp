package gr.nifitsas.dealsapp.controller;

import gr.nifitsas.dealsapp.core.authentication.AuthService;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotAuthorizedException;
import gr.nifitsas.dealsapp.dto.AuthRequestDTO;
import gr.nifitsas.dealsapp.dto.AuthResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerJunitTest {

  @Mock
  private AuthService authService;

  private AuthRestController authRestController;

  @BeforeEach
  public void setUp() {
    authRestController = new AuthRestController(authService);
  }

  @Test
  public void testAuthenticate_success() throws AppObjectNotAuthorizedException {

    AuthRequestDTO requestDTO = new AuthRequestDTO("test@email.com", "password");
    AuthResponseDTO responseDTO = new AuthResponseDTO("test@email.com","token");
    when(authService.authenticate(requestDTO)).thenReturn(responseDTO);
    ResponseEntity<AuthResponseDTO> response = authRestController.authenticate(requestDTO);
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(responseDTO, response.getBody());
    verify(authService, times(1)).authenticate(requestDTO);
  }

  @Test
  public void testAuthenticate_throwsException() throws AppObjectNotAuthorizedException {
    // Mock AuthService behavior to throw exception
    AuthRequestDTO requestDTO = new AuthRequestDTO("test@email.com", "password");
    when(authService.authenticate(requestDTO)).thenThrow(new AppObjectNotAuthorizedException("User", "Invalid credentials"));

    // Call the controller method and expect the exception to be propagated
    assertThrows(AppObjectNotAuthorizedException.class, () -> authRestController.authenticate(requestDTO));
    verify(authService, times(1)).authenticate(requestDTO);
  }
}
