package gr.nifitsas.dealsapp.controller;

import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.core.mapper.Mapper;
import gr.nifitsas.dealsapp.dto.UserDTOs.UserInsertDTO;
import gr.nifitsas.dealsapp.dto.UserDTOs.UserReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.productDTOs.ProductReadOnlyDTO;
import gr.nifitsas.dealsapp.model.static_data.Store;
import gr.nifitsas.dealsapp.repository.UserRepository;
import gr.nifitsas.dealsapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  /**
   * Retrieves a list of all users.
   *
   * @return A ResponseEntity containing a list of UserReadOnlyDTO objects and an HTTP status of OK (200).
   * @throws Exception If an unexpected error occurs during the retrieval process.
   */
  //OpenAPI Annotations
  @Operation(summary = "Retrieves all users" )
  @ApiResponses(value = {
    @ApiResponse(
        responseCode = "200", description = "Users retrieved",
        content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = UserReadOnlyDTO.class)) })})
  //Controller
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
  /**
   * Retrieves a user by their username.
   *
   * @param username The username of the user to retrieve.
   * @return A ResponseEntity containing an Optional of UserReadOnlyDTO, or an empty Optional if no user is found.
   * @throws Exception If an unexpected error occurs during the retrieval process.
   */
  //OpenAPI Annotations
  @Operation(summary = "Retrieves a user info by it's username" )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Users retrieved",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = UserReadOnlyDTO.class)) }),
    @ApiResponse(
      responseCode = "404",
      description = "User cannot be found",
      content = @Content(mediaType = "application/json")
    )
  })
  //Controller
  @GetMapping("/find")
  public ResponseEntity<Optional<UserReadOnlyDTO>> getUserByUsername(@RequestParam("username") String username) throws AppObjectNotFoundException {
    Optional<UserReadOnlyDTO> user = userService.findUserByUsername(username);
    if (user.isEmpty()) {
      throw new AppObjectNotFoundException("User", "User with username : "+ username + " was not found");
    }
    try {
      return new ResponseEntity<>(user, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("ERROR: Could not find User.", e);
      throw e;
    }
  }

  /**
   * Creates a new user.
   *
   * @param userInsertDTO The UserInsertDTO object containing the user data.
   * @return A ResponseEntity containing the created UserReadOnlyDTO object and an HTTP status of CREATED (201).
   * @throws AppObjectInvalidArgumentException If the provided user data is invalid.
   * @throws AppObjectAlreadyExistsException If a user with the same username already exists.
   */
  //OpenAPI Annotations

  @Operation(summary = "Creates a new user with USER role" )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "201",
      description = "Users created",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = UserReadOnlyDTO.class)) }),
    @ApiResponse(
      responseCode = "400",
      description = "Invalid username or Password",
      content = @Content(mediaType = "application/json")
    )
  })
  //Controller
  @PostMapping("/add")
  public ResponseEntity<UserReadOnlyDTO> addUser(@RequestBody @Valid UserInsertDTO userInsertDTO) throws AppObjectInvalidArgumentException, AppObjectAlreadyExistsException {
    try{
      UserReadOnlyDTO user = userService.saveUser(userInsertDTO);
      return new ResponseEntity<>(user, HttpStatus.CREATED);
    }catch (AppObjectAlreadyExistsException | AppObjectInvalidArgumentException e){
    LOGGER.error("ERROR: Couldn't add user", e);
    throw e;
    }
  }


  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(summary = " Deletes a User" )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "User deleted succesfully",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = UserReadOnlyDTO.class)) }),
    @ApiResponse(responseCode = "401", description = "Authentication Error",
      content = @Content),
    @ApiResponse(responseCode = "404", description = "User Not Found",
      content = @Content),
    @ApiResponse(responseCode = "400", description = "You tried to delete an Admin user",
      content = @Content)})
  //Controller
  @DeleteMapping("/remove/{uuid}")
  public ResponseEntity<UserReadOnlyDTO>deleteUser(@PathVariable("uuid")String uuid) throws AppObjectInvalidArgumentException, AppObjectNotFoundException  {
    try{
      var usernameToDelete = userService.findUserByUuid(uuid);
      if(usernameToDelete.orElseThrow().getRole().toString().equals("ADMIN")){
        throw new AppObjectInvalidArgumentException("User", "You cannot delete an administrator");
      }
      UserReadOnlyDTO deletedUser = userService.deleteUser(uuid);
      return new ResponseEntity<>(deletedUser, HttpStatus.OK);
    }catch (AppObjectInvalidArgumentException | AppObjectNotFoundException e){
      LOGGER.error("ERROR: Could not delete user.", e);
      throw e;
    }
  }
}
