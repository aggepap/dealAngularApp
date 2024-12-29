package gr.nifitsas.dealsapp.service;

import gr.nifitsas.dealsapp.core.enums.Role;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.core.mapper.Mapper;
import gr.nifitsas.dealsapp.dto.UserDTOs.UserInsertDTO;
import gr.nifitsas.dealsapp.dto.UserDTOs.UserReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.productDTOs.ProductReadOnlyDTO;
import gr.nifitsas.dealsapp.model.Product;
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

  /**
   * Finds a user by their username.
   *
   * @param username The username of the user to find.
   * @return An Optional containing a UserReadOnlyDTO if the user is found,
   * otherwise empty.
   */
  public Optional<UserReadOnlyDTO> findUserByUsername(String username) {
    Optional<User> user = userRepository.findByUsername(username);
    return user.map(mapper::mapToUserReadOnlyDTO);
  }

  /**
   * Finds a user by their uuid.
   *
   * @param uuid The uuid of the user to find.
   * @return An Optional containing a UserReadOnlyDTO if the user is found,
   * otherwise empty.
   */
  public Optional<UserReadOnlyDTO> findUserByUuid(String uuid) {
    Optional<User> user = userRepository.findByUuid(uuid);
    return user.map(mapper::mapToUserReadOnlyDTO);
  }


  /**
   * Saves a new user to the database.
   *
   * @param userInsertDTO User data to be saved.
   * @return A UserReadOnlyDTO representing the saved user.
   * @throws AppObjectAlreadyExistsException If a user with the same username already exists.
   * @throws AppObjectInvalidArgumentException If the provided user data is invalid.
   */
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

  /**
   * Retrieves all users from the database.
   *
   * @return A list of UserReadOnlyDTOs representing all users.
   */
  public List<UserReadOnlyDTO> getAllUsers () {
    return userRepository.findAll().stream().map(mapper::mapToUserReadOnlyDTO).collect(Collectors.toList());
  }



  /**
   * Deletes a user by their UUID.
   *
   * @param uuid The UUID of the user to delete.
   * @return A UserReadOnlyDTO representing the deleted user.
   * @throws AppObjectNotFoundException If a user with the given UUID is not found.
   * @throws AppObjectInvalidArgumentException If the provided UUID is invalid.
   */
  @Transactional(rollbackOn = Exception.class)
  public UserReadOnlyDTO deleteUser(String uuid) throws AppObjectNotFoundException, AppObjectInvalidArgumentException {
    Optional<User> optionalUser = userRepository.findByUuid(uuid);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      userRepository.delete(user);
      return mapper.mapToUserReadOnlyDTO(user);
    } else {
      throw new AppObjectNotFoundException("User", "User with id: " + uuid + " not found");
    }
  }


}
