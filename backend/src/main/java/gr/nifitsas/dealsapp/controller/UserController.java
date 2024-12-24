package gr.nifitsas.dealsapp.controller;

import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppServerException;
import gr.nifitsas.dealsapp.core.exceptions.ValidationException;
import gr.nifitsas.dealsapp.core.mapper.Mapper;
import gr.nifitsas.dealsapp.dto.UserDTOs.UserInsertDTO;
import gr.nifitsas.dealsapp.dto.UserDTOs.UserReadOnlyDTO;
import gr.nifitsas.dealsapp.repository.UserRepository;
import gr.nifitsas.dealsapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserRepository userRepository;
  private final Mapper mapper;
  private final UserService userService;
  private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  @GetMapping("")
  public ResponseEntity<List<UserReadOnlyDTO>> getAllUsers() {
    List<UserReadOnlyDTO> users = userService.getAllUsers();
    try {
      return new ResponseEntity<>(users, HttpStatus.OK);
    }catch (Exception e){
      LOGGER.error("ERROR: Could not get Users", e.getMessage());
      throw e;

    }
  }

  @GetMapping("/find")
  public ResponseEntity<Optional<UserReadOnlyDTO>> getUserByUsername(@RequestParam("username") String username) {
    Optional<UserReadOnlyDTO> user = userService.findUserByUsername(username);
    try {
      return new ResponseEntity<>(user, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("ERROR: Could not find User.", e);
      throw e;
    }
  }

  @PostMapping("/add")
  public ResponseEntity<UserReadOnlyDTO> addUser(@RequestBody @Valid UserInsertDTO userInsertDTO, BindingResult bindingResult) throws AppObjectInvalidArgumentException, AppObjectAlreadyExistsException {
    try{
      UserReadOnlyDTO user = userService.saveUser(userInsertDTO);
      return new ResponseEntity<>(user, HttpStatus.CREATED);
    }catch (AppObjectAlreadyExistsException | AppObjectInvalidArgumentException e){
    LOGGER.error("ERROR: Couldn't add user", e);
    throw e;
    }
  }
}
