package gr.nifitsas.dealsapp.controller;


import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExists;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.core.exceptions.AppServerException;
import gr.nifitsas.dealsapp.dto.StoreDTOs.StoreInsertDTO;
import gr.nifitsas.dealsapp.dto.StoreDTOs.StoreReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.StoreDTOs.StoreUpdateDTO;
import gr.nifitsas.dealsapp.dto.productDTOs.ProductUpdateDTO;
import gr.nifitsas.dealsapp.model.Product;
import gr.nifitsas.dealsapp.service.StoreService;
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


  @PostMapping("/add")
  public ResponseEntity<StoreReadOnlyDTO> addStore(
    @RequestPart(name = "store") @Valid StoreInsertDTO storeInsertDTO,
    @RequestPart(name = "logo", required = true) MultipartFile logo

  ) throws AppObjectInvalidArgumentException, AppObjectAlreadyExists, IOException {
    try {
      StoreReadOnlyDTO store = storeService.saveStore(storeInsertDTO, logo);
      return new ResponseEntity<>(store, HttpStatus.CREATED);
    } catch (AppObjectAlreadyExists | AppObjectInvalidArgumentException | IOException e) {
      LOGGER.error("ERROR: Could not add Store.", e);
      throw e;
    }

  }
  @PutMapping("/update/{storeId}")
  public ResponseEntity<StoreReadOnlyDTO> updateStore(
    @PathVariable("storeId") Long storeId,
    @RequestPart(name = "store") @Valid StoreUpdateDTO storeUpdateDTO,
    @RequestPart(name = "image", required = false) MultipartFile image
  ) throws AppObjectAlreadyExists, AppObjectNotFoundException, IOException {
    try {
      StoreReadOnlyDTO category = storeService.updateStore(storeId,storeUpdateDTO, image);
      return new ResponseEntity<>(category, HttpStatus.CREATED);
    } catch (AppObjectAlreadyExists | AppObjectNotFoundException e) {
      LOGGER.error("ERROR: Could not update Store." + storeUpdateDTO.getName(), e);
      throw e;
    }

  }

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
