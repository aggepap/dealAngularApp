package gr.nifitsas.dealsapp.controller;

import gr.nifitsas.dealsapp.controller.StoreController;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.core.mapper.Mapper;
import gr.nifitsas.dealsapp.dto.StoreDTOs.StoreInsertDTO;
import gr.nifitsas.dealsapp.dto.StoreDTOs.StoreReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.StoreDTOs.StoreUpdateDTO;
import gr.nifitsas.dealsapp.model.Product;
import gr.nifitsas.dealsapp.model.static_data.Store;
import gr.nifitsas.dealsapp.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreControllerJunitTest {

  @Mock
  private StoreService storeService;

  private Mapper mapper;
  private StoreController storeController;

  @BeforeEach
  public void setUp() {
    storeController = new StoreController(storeService);
  }

  // Test getAllStores - Success (Stores Found)
  @Test
  public void testGetAllStores_success() throws Exception {
    List<StoreReadOnlyDTO> storeList = Collections.singletonList(new StoreReadOnlyDTO());
    when(storeService.findAllStores()).thenReturn(storeList);

    ResponseEntity<List<StoreReadOnlyDTO>> response = storeController.getAllStores();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(storeList, response.getBody());
    verify(storeService, times(1)).findAllStores();
  }

  // Test getAllStores - Throws Exception
  @Test
  public void testGetAllStores_throwsException() throws Exception {
    when(storeService.findAllStores()).thenThrow(new RuntimeException("Test Exception"));

    assertThrows(Exception.class, () -> storeController.getAllStores());
    verify(storeService, times(1)).findAllStores();
  }

  // Test getStoreById - Success (Store Found)
  @Test
  public void testGetStoreById_success() throws AppObjectNotFoundException {
    Long id = 1L;
    Optional<StoreReadOnlyDTO> store = Optional.of(new StoreReadOnlyDTO());
    when(storeService.findStoreById(id)).thenReturn(store);

    ResponseEntity<Optional<StoreReadOnlyDTO>> response = storeController.getStoreById(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(store, response.getBody());
    verify(storeService, times(1)).findStoreById(id);
  }

  // Test getStoreById - Not Found
  @Test
  public void testGetStoreById_notFound() throws AppObjectNotFoundException {
    Long id = 1L;
    when(storeService.findStoreById(id)).thenReturn(Optional.empty());

    assertThrows(AppObjectNotFoundException.class, () -> storeController.getStoreById(id));
    verify(storeService, times(1)).findStoreById(id);
  }

  // Test getStoreById - Service throws exception
  @Test
  public void testGetStoreById_serviceThrowsException() throws AppObjectNotFoundException {
    Long id = 1L;
    when(storeService.findStoreById(id)).thenThrow(new RuntimeException("Test Exception"));

    assertThrows(RuntimeException.class, () -> storeController.getStoreById(id));
    verify(storeService, times(1)).findStoreById(id);
  }

  // Test getStoreProducts - Success (Store Found and Products Retrieved)
  @Test
  public void testGetStoreProducts_success() throws AppObjectNotFoundException {
    Long id = 1L;
    List<Product> products = Collections.singletonList(new Product());
    when(storeService.findStoreById(id)).thenReturn(Optional.<StoreReadOnlyDTO>of(new StoreReadOnlyDTO()));
    when(storeService.findAllStoreDeals(id)).thenReturn(products);

    ResponseEntity<List<Product>> response = storeController.getStoreProducts(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(products, response.getBody());
    verify(storeService, times(1)).findStoreById(id);
    verify(storeService, times(1)).findAllStoreDeals(id);
  }

  // Test getStoreProducts - Store Not Found
  @Test
  public void testGetStoreProducts_storeNotFound() throws AppObjectNotFoundException {
    Long id = 1L;
    when(storeService.findStoreById(id)).thenReturn(Optional.empty());

    assertThrows(AppObjectNotFoundException.class, () -> storeController.getStoreProducts(id));
    verify(storeService, times(1)).findStoreById(id);
    verify(storeService, times(0)).findAllStoreDeals(id); // Ensure findAllStoreDeals is not called
  }

  // Test getStoreProducts - Service throws exception
  @Test
  public void testGetStoreProducts_serviceThrowsException() throws AppObjectNotFoundException {
    Long id = 1L;
    when(storeService.findStoreById(id)).thenReturn(Optional.of(new StoreReadOnlyDTO()));
    when(storeService.findAllStoreDeals(id)).thenThrow(new RuntimeException("Test Exception"));

    assertThrows(RuntimeException.class, () -> storeController.getStoreProducts(id));
    verify(storeService, times(1)).findStoreById(id);
    verify(storeService, times(1)).findAllStoreDeals(id);
  }

  // Test addStore - Success
  @Test
  public void testAddStore_success() throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException {
    StoreInsertDTO storeInsertDTO = new StoreInsertDTO();
    MultipartFile logo = new MockMultipartFile("logo", "logo.png", "image/png", "logo data".getBytes());
    StoreReadOnlyDTO createdStore = new StoreReadOnlyDTO();
    when(storeService.saveStore(storeInsertDTO, logo)).thenReturn(createdStore);

    ResponseEntity<StoreReadOnlyDTO> response = storeController.addStore(storeInsertDTO, logo);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(createdStore, response.getBody());
    verify(storeService, times(1)).saveStore(storeInsertDTO, logo);
  }

  // Test addStore - Service throws AppObjectAlreadyExistsException
  @Test
  public void testAddStore_throwsAppObjectAlreadyExistsException() throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException {
    StoreInsertDTO storeInsertDTO = new StoreInsertDTO();
    MultipartFile logo = new MockMultipartFile("logo", "logo.png", "image/png", "logo data".getBytes());
    when(storeService.saveStore(storeInsertDTO, logo)).thenThrow(new AppObjectAlreadyExistsException("Store", "Store already exists"));

    assertThrows(AppObjectAlreadyExistsException.class, () -> storeController.addStore(storeInsertDTO, logo));
    verify(storeService, times(1)).saveStore(storeInsertDTO, logo);
  }

  // Test addStore - Service throws AppObjectInvalidArgumentException
  @Test
  public void testAddStore_throwsAppObjectInvalidArgumentException() throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException {
    StoreInsertDTO storeInsertDTO = new StoreInsertDTO();
    MultipartFile logo = new MockMultipartFile("logo", "logo.png", "image/png", "logo data".getBytes());
    when(storeService.saveStore(storeInsertDTO, logo)).thenThrow(new AppObjectInvalidArgumentException("Store", "Invalid store data"));

    assertThrows(AppObjectInvalidArgumentException.class, () -> storeController.addStore(storeInsertDTO, logo));
    verify(storeService, times(1)).saveStore(storeInsertDTO, logo);
  }

  // Test addStore - Service throws IOException
  @Test
  public void testAddStore_throwsIOException() throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException {
    StoreInsertDTO storeInsertDTO = new StoreInsertDTO();
    MultipartFile logo = new MockMultipartFile("logo", "logo.png", "image/png", "logo data".getBytes());
    when(storeService.saveStore(storeInsertDTO, logo)).thenThrow(new IOException("Logo upload failed"));

    assertThrows(IOException.class, () -> storeController.addStore(storeInsertDTO, logo));
    verify(storeService, times(1)).saveStore(storeInsertDTO, logo);
  }

  // Test updateStore - Success
  @Test
  public void testUpdateStore_success() throws AppObjectNotFoundException, AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException {
    Long storeId = 1L;
    StoreUpdateDTO storeUpdateDTO = new StoreUpdateDTO();
    MultipartFile logo = new MockMultipartFile("logo", "logo.png", "image/png", "logo data".getBytes());
    StoreReadOnlyDTO updatedStore = new StoreReadOnlyDTO();
    when(storeService.updateStore(storeId, storeUpdateDTO, logo)).thenReturn(updatedStore);

    ResponseEntity<StoreReadOnlyDTO> response = storeController.updateStore(storeId, storeUpdateDTO, logo);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(updatedStore, response.getBody());
    verify(storeService, times(1)).updateStore(storeId, storeUpdateDTO, logo);
  }

  // Test updateStore - Service throws AppObjectNotFoundException
  @Test
  public void testUpdateStore_throwsAppObjectNotFoundException() throws AppObjectNotFoundException, AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException {
    Long storeId = 1L;
    StoreUpdateDTO storeUpdateDTO = new StoreUpdateDTO();
    MultipartFile logo = new MockMultipartFile("logo", "logo.png", "image/png", "logo data".getBytes());
    when(storeService.updateStore(storeId, storeUpdateDTO, logo)).thenThrow(new AppObjectNotFoundException("Store", "Store not found"));

    assertThrows(AppObjectNotFoundException.class, () -> storeController.updateStore(storeId, storeUpdateDTO, logo));
    verify(storeService, times(1)).updateStore(storeId, storeUpdateDTO, logo);
  }

  // Test updateStore - Service throws AppObjectAlreadyExistsException
  @Test
  public void testUpdateStore_throwsAppObjectAlreadyExistsException() throws AppObjectNotFoundException, AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException {
    Long storeId = 1L;
    StoreUpdateDTO storeUpdateDTO = new StoreUpdateDTO();
    MultipartFile logo = new MockMultipartFile("logo", "logo.png", "image/png", "logo data".getBytes());
    when(storeService.updateStore(storeId, storeUpdateDTO, logo)).thenThrow(new AppObjectAlreadyExistsException("Store", "Store already exists"));

    assertThrows(AppObjectAlreadyExistsException.class, () -> storeController.updateStore(storeId, storeUpdateDTO, logo));
    verify(storeService, times(1)).updateStore(storeId, storeUpdateDTO, logo);
  }

  // Test updateStore - Service throws AppObjectInvalidArgumentException
  @Test
  public void testUpdateStore_throwsAppObjectInvalidArgumentException() throws AppObjectNotFoundException, AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException {
    Long storeId = 1L;
    StoreUpdateDTO storeUpdateDTO = new StoreUpdateDTO();
    MultipartFile logo = new MockMultipartFile("logo", "logo.png", "image/png", "logo data".getBytes());
    when(storeService.updateStore(storeId, storeUpdateDTO, logo)).thenThrow(new AppObjectInvalidArgumentException("Store", "Invalid store data"));

    assertThrows(AppObjectInvalidArgumentException.class, () -> storeController.updateStore(storeId, storeUpdateDTO, logo));
    verify(storeService, times(1)).updateStore(storeId, storeUpdateDTO, logo);
  }

  // Test updateStore - Service throws IOException
  @Test
  public void testUpdateStore_throwsIOException() throws AppObjectNotFoundException, AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException {
    Long storeId = 1L;
    StoreUpdateDTO storeUpdateDTO = new StoreUpdateDTO();
    MultipartFile logo = new MockMultipartFile("logo", "logo.png", "image/png", "logo data".getBytes());
    when(storeService.updateStore(storeId, storeUpdateDTO, logo)).thenThrow(new IOException("Logo upload failed"));

    assertThrows(IOException.class, () -> storeController.updateStore(storeId, storeUpdateDTO, logo));
    verify(storeService, times(1)).updateStore(storeId, storeUpdateDTO, logo);
  }

  // Test deleteStore - Success
  @Test
  public void testDeleteStore_success() throws AppObjectNotFoundException, AppObjectInvalidArgumentException {
    Long id = 1L;
    StoreReadOnlyDTO deletedStore = new StoreReadOnlyDTO();
    when(storeService.findStoreById(id)).thenReturn(Optional.of(new StoreReadOnlyDTO()));
    when(storeService.deleteStore(id)).thenReturn(deletedStore);

    ResponseEntity<StoreReadOnlyDTO> response = storeController.deleteStore(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(deletedStore, response.getBody());
    verify(storeService, times(1)).findStoreById(id);
    verify(storeService, times(1)).deleteStore(id);
  }

  // Test deleteStore - Store Not Found
  @Test
  public void testDeleteStore_notFound() throws AppObjectNotFoundException, AppObjectInvalidArgumentException {
    Long id = 1L;
    when(storeService.findStoreById(id)).thenReturn(Optional.empty());

    assertThrows(AppObjectNotFoundException.class, () -> storeController.deleteStore(id));
    verify(storeService, times(1)).findStoreById(id);
    verify(storeService, times(0)).deleteStore(id); // Ensure deleteStore is not called
  }

  // Test deleteStore - Service throws AppObjectInvalidArgumentException
  @Test
  public void testDeleteStore_throwsAppObjectInvalidArgumentException() throws AppObjectNotFoundException, AppObjectInvalidArgumentException {
    Long id = 1L;
    when(storeService.findStoreById(id)).thenReturn(Optional.of(new StoreReadOnlyDTO()));
    when(storeService.deleteStore(id)).thenThrow(new AppObjectInvalidArgumentException("Store", "Cannot delete 'Other' store"));

    assertThrows(AppObjectInvalidArgumentException.class, () -> storeController.deleteStore(id));
    verify(storeService, times(1)).findStoreById(id);
    verify(storeService, times(1)).deleteStore(id);
  }
}
