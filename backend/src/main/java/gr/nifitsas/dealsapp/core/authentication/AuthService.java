package gr.nifitsas.dealsapp.core.authentication;

import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotAuthorizedException;
import gr.nifitsas.dealsapp.core.security.JwtService;
import gr.nifitsas.dealsapp.dto.AuthRequestDTO;
import gr.nifitsas.dealsapp.dto.AuthResponseDTO;
import gr.nifitsas.dealsapp.model.User;
import gr.nifitsas.dealsapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final JwtService jwtService;


  public AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) throws AppObjectNotAuthorizedException{

  Authentication authentication = authenticationManager.authenticate(
    new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
    User user = userRepository.findByUsername(authentication.getName())
      .orElseThrow(()-> new AppObjectNotAuthorizedException("User","User not authorized"));
    String token = jwtService.generateToken(authentication.getName(), user.getRole().name());
    return new AuthResponseDTO(user.getUsername(), token);
  }
}
