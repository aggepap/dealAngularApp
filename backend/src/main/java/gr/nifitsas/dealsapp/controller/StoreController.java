package gr.nifitsas.dealsapp.controller;


import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.dto.StoreDTOs.StoreInsertDTO;
import gr.nifitsas.dealsapp.dto.StoreDTOs.StoreReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.StoreDTOs.StoreUpdateDTO;
import gr.nifitsas.dealsapp.model.Product;
import gr.nifitsas.dealsapp.model.static_data.Store;
import gr.nifitsas.dealsapp.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {
  private final StoreService storeService;
  private final Logger LOGGER = LoggerFactory.getLogger(StoreController.class);


  /**
   * Retrieves a list of all stores.
   *
   * @return A ResponseEntity containing a list of StoreReadOnlyDTO objects and an HTTP status of OK (200).
   * @throws Exception Any other unexpected error that may occur during retrieval.
   */
  //OpenAPI Annotations
  @Operation(summary = "Retrieves a list of all stores." )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Stores found and listed, or not found and returns an empty list",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = Store.class)) })})
  //Controller
  @GetMapping("")
  public ResponseEntity<List<StoreReadOnlyDTO>> getAllStores() {
    List<StoreReadOnlyDTO> storeList = storeService.findAllStores();
    try {
      return new ResponseEntity<>(storeList, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("ERROR: Could not get Stores.", e);
      throw e;
    }
  }

  /**
   * Retrieves a store by its ID.
   *
   * @param id The ID of the store to retrieve.
   * @return A ResponseEntity containing an Optional of StoreReadOnlyDTO, or an empty Optional if no store is found.
   * @throws Exception Any other unexpected error that may occur during retrieval.
   */
  //OpenAPI Annotations
  @Operation(summary = "Retrieves a store by its ID" )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Store found",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = Store.class)) }),
    @ApiResponse(responseCode = "404", description = "Store not found",
      content = @Content)})
  //Controller
  @GetMapping("/find")
  public ResponseEntity<Optional<StoreReadOnlyDTO>> getStoreById(@RequestParam("id") Long id) {
    Optional<StoreReadOnlyDTO> store = storeService.findStoreById(id);
    try {
      return new ResponseEntity<>(store, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("ERROR: Could not get Stores.", e);
      throw e;
    }
  }
  /**
   * Retrieves all products associated with a specific store.
   *
   * @param id The ID of the store.
   * @return A ResponseEntity containing a list of Product objects and an HTTP status of OK (200).
   * @throws Exception Any other unexpected error that may occur during retrieval.
   */
  //OpenAPI Annotations
  @Operation(summary = "Retrieves all products associated with a specific store" )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Store found and products fetched",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = Store.class)) }),
    @ApiResponse(responseCode = "404", description = "Store not found",
      content = @Content)})
  //Controller
  @GetMapping("/{id}/products")
  public ResponseEntity<List<Product>> getStoreProducts(@PathVariable("id") Long id) {
    List<Product> storeProducts = storeService.findAllStoreDeals(id);
    try {
      return new ResponseEntity<>(storeProducts, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("ERROR: Could not get products for this store.", e);
      throw e;
    }
  }

  /**
   * Creates a new store.
   *
   * @param storeInsertDTO The StoreInsertDTO object containing the store data.
   * @param logo The MultipartFile containing the store logo.
   * @return A ResponseEntity containing the created StoreReadOnlyDTO object and an HTTP status of CREATED (201).
   * @throws AppObjectAlreadyExistsException If a store with the same name already exists.
   * @throws AppObjectInvalidArgumentException If the provided store data is invalid.
   * @throws IOException If an error occurs while saving the store logo.
   */
  //OpenAPI Annotations
  @Operation(summary = "Creates a new store." )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Store Created",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = Store.class)) }),
    @ApiResponse(responseCode = "409", description = "Store Already exists",
      content = @Content),
    @ApiResponse(responseCode = "400", description = "Invalid arguments",
      content = @Content)})
  //Controller
  @PostMapping("/add")
  public ResponseEntity<StoreReadOnlyDTO> addStore(
    @RequestPart(name = "store") @Valid StoreInsertDTO storeInsertDTO,
    @RequestPart(name = "logo", required = true) MultipartFile logo

  ) throws AppObjectInvalidArgumentException, AppObjectAlreadyExistsException, IOException {
    try {
      StoreReadOnlyDTO store = storeService.saveStore(storeInsertDTO, logo);
      return new ResponseEntity<>(store, HttpStatus.CREATED);
    } catch (AppObjectAlreadyExistsException | AppObjectInvalidArgumentException | IOException e) {
      LOGGER.error("ERROR: Could not add Store.", e);
      throw e;
    }

  }

  /**
   * Updates an existing store.
   *
   * @param storeId The ID of the store to update.
   * @param storeUpdateDTO The StoreUpdateDTO object containing the updated store data.
   * @param image The MultipartFile containing the updated store logo (optional).
   * @return A ResponseEntity containing the updated StoreReadOnlyDTO object and an HTTP status of CREATED (201).
   * @throws AppObjectAlreadyExistsException If a store with the same name already exists.
   * @throws AppObjectNotFoundException If the store with the specified ID is not found.
   * @throws IOException If an error occurs while saving the store logo.
   */
  //OpenAPI Annotations
  @Operation(summary = " Updates an existing store")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Store updated",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = Store.class)) }),
    @ApiResponse(responseCode = "404", description = "Store not found",
      content = @Content) })
  //Controller
  @PutMapping("/update/{storeId}")
  public ResponseEntity<StoreReadOnlyDTO> updateStore(
    @PathVariable("storeId") Long storeId,
    @RequestPart(name = "store") @Valid StoreUpdateDTO storeUpdateDTO,
    @RequestPart(name = "image", required = false) MultipartFile image
  ) throws AppObjectAlreadyExistsException, AppObjectNotFoundException, IOException {
    try {
      StoreReadOnlyDTO category = storeService.updateStore(storeId,storeUpdateDTO, image);
      return new ResponseEntity<>(category, HttpStatus.CREATED);
    } catch (AppObjectAlreadyExistsException | AppObjectNotFoundException e) {
      LOGGER.error("ERROR: Could not update Store." + storeUpdateDTO.getName(), e);
      throw e;
    }

  }

  /**
   * Deletes a store.
   *
   * @param id The ID of the store to delete.
   * @return A ResponseEntity containing the deleted StoreReadOnlyDTO object and an HTTP status of OK (200).
   * @throws AppObjectInvalidArgumentException If the store to be deleted is the "Other" store.
   * @throws AppObjectNotFoundException If the store with the specified ID is not found.
   */
  //OpenAPI Annotations
  @Operation(summary = " Deletes a store.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Store Deleted",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = Store.class)) }),
    @ApiResponse(responseCode = "404", description = "Store not found",
      content = @Content) })
  //Controller
  @DeleteMapping("/remove")
  public ResponseEntity<StoreReadOnlyDTO>deleteStore(@RequestParam("id") Long id) throws AppObjectInvalidArgumentException, AppObjectNotFoundException  {
    try{
      StoreReadOnlyDTO deletedStore = storeService.deleteStore(id);
      return new ResponseEntity<>(deletedStore, HttpStatus.OK);
    }catch (AppObjectInvalidArgumentException | AppObjectNotFoundException e){
      LOGGER.error("ERROR: Could not delete Store.", e);
      throw e;
    }
  }
}
