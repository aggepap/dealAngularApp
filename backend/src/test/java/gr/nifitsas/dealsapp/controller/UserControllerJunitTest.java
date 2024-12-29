package gr.nifitsas.dealsapp.controller;

import gr.nifitsas.dealsapp.core.enums.Role;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.dto.UserDTOs.UserInsertDTO;
import gr.nifitsas.dealsapp.dto.UserDTOs.UserReadOnlyDTO;
import gr.nifitsas.dealsapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerJunitTest {

  @Mock
  private UserService userService;

  private UserController userController;

  @BeforeEach
  public void setUp() {
    userController = new UserController(userService);
  }

  // Test getAllUsers - Success (Users Found)
  @Test
  public void testGetAllUsers_success() throws Exception {
    List<UserReadOnlyDTO> users = Collections.singletonList(new UserReadOnlyDTO());
    when(userService.getAllUsers()).thenReturn(users);

    ResponseEntity<List<UserReadOnlyDTO>> response = userController.getAllUsers();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(users, response.getBody());
    verify(userService, times(1)).getAllUsers();
  }

  // Test getAllUsers - Throws Exception
  @Test
  public void testGetAllUsers_throwsException() throws Exception {
    when(userService.getAllUsers()).thenThrow(new RuntimeException("Test Exception"));

    assertThrows(Exception.class, () -> userController.getAllUsers());
    verify(userService, times(1)).getAllUsers();
  }

  // Test getUserByUsername - Success (User Found)
  @Test
  public void testGetUserByUsername_success() throws AppObjectNotFoundException {
    String username = "test_user";
    Optional<UserReadOnlyDTO> user = Optional.of(new UserReadOnlyDTO());
    when(userService.findUserByUsername(username)).thenReturn(user);

    ResponseEntity<Optional<UserReadOnlyDTO>> response = userController.getUserByUsername(username);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(user, response.getBody());
    verify(userService, times(1)).findUserByUsername(username);
  }

  // Test getUserByUsername - Not Found
  @Test
  public void testGetUserByUsername_notFound() throws AppObjectNotFoundException {
    String username = "test_user";
    when(userService.findUserByUsername(username)).thenReturn(Optional.empty());

    assertThrows(AppObjectNotFoundException.class, () -> userController.getUserByUsername(username));
    verify(userService, times(1)).findUserByUsername(username);
  }

  // Test getUserByUsername - Service throws exception
  @Test
  public void testGetUserByUsername_serviceThrowsException() throws AppObjectNotFoundException {
    String username = "test_user";
    when(userService.findUserByUsername(username)).thenThrow(new RuntimeException("Test Exception"));

    assertThrows(RuntimeException.class, () -> userController.getUserByUsername(username));
    verify(userService, times(1)).findUserByUsername(username);
  }

  // Test addUser - Success
  @Test
  public void testAddUser_success() throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException {
    UserInsertDTO userInsertDTO = new UserInsertDTO();
    userInsertDTO.setUsername("test@example.com"); // Valid email
    userInsertDTO.setPassword("P@$$wOrd123"); // Valid password
    userInsertDTO.setRole(Role.USER); // Set the role
    UserReadOnlyDTO createdUser = new UserReadOnlyDTO();
    when(userService.saveUser(userInsertDTO)).thenReturn(createdUser);

    ResponseEntity<UserReadOnlyDTO> response = userController.addUser(userInsertDTO);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(createdUser, response.getBody());
    verify(userService, times(1)).saveUser(argThat(dto -> dto.getRole() == Role.USER));
  }

  // Test addUser - Service throws AppObjectAlreadyExistsException
  @Test
  public void testAddUser_throwsAppObjectAlreadyExistsException() throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException {
    UserInsertDTO userInsertDTO = new UserInsertDTO();
    when(userService.saveUser(userInsertDTO)).thenThrow(new AppObjectAlreadyExistsException("User", "User already exists"));

    assertThrows(AppObjectAlreadyExistsException.class, () -> userController.addUser(userInsertDTO));
    verify(userService, times(1)).saveUser(userInsertDTO);
  }

  // Test addUser - Service throws AppObjectInvalidArgumentException
  @Test
  public void testAddUser_throwsAppObjectInvalidArgumentException() throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException {
    UserInsertDTO userInsertDTO = new UserInsertDTO();
    when(userService.saveUser(userInsertDTO)).thenThrow(new AppObjectInvalidArgumentException("User", "Invalid user data"));

    assertThrows(AppObjectInvalidArgumentException.class, () -> userController.addUser(userInsertDTO));
    verify(userService, times(1)).saveUser(userInsertDTO);
  }
}

