package gr.nifitsas.dealsapp.service;

import gr.nifitsas.dealsapp.core.enums.Role;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.mapper.Mapper;
import gr.nifitsas.dealsapp.dto.UserDTOs.UserInsertDTO;
import gr.nifitsas.dealsapp.dto.UserDTOs.UserReadOnlyDTO;
import gr.nifitsas.dealsapp.model.User;
import gr.nifitsas.dealsapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  private final Mapper mapper;
  private final PasswordEncoder passwordEncoder;

  public Optional<UserReadOnlyDTO> findUserByUsername(String username) {
    Optional<User> user = userRepository.findByUsername(username);
    return user.map(mapper::mapToUserReadOnlyDTO);
  }


  @Transactional(rollbackOn = Exception.class)
  public UserReadOnlyDTO saveUser (UserInsertDTO userInsertDTO) throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException {
    if(userRepository.findByUsername(userInsertDTO.getUsername()).isPresent()) {
      throw new AppObjectAlreadyExistsException("User","User with username: "+ userInsertDTO.getUsername() + " already exists ");
    }
    User user = mapper.mapToUserEntity(userInsertDTO);
    user.setPassword(passwordEncoder.encode(userInsertDTO.getPassword()));
    if(user.getRole() == null){
      user.setRole(Role.USER);
    }
    if(user.getIsActive() == null){
      user.setIsActive(true);
    }
    userRepository.save(user);
    return mapper.mapToUserReadOnlyDTO(user);
  }

  public List<UserReadOnlyDTO> getAllUsers () {
    return userRepository.findAll().stream().map(mapper::mapToUserReadOnlyDTO).collect(Collectors.toList());
  }

}
