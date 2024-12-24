package gr.nifitsas.dealsapp.controller;

import gr.nifitsas.dealsapp.core.exceptions.*;
import gr.nifitsas.dealsapp.core.filters.Paginated;
import gr.nifitsas.dealsapp.core.filters.ProductFilters;
import gr.nifitsas.dealsapp.dto.productDTOs.ProductInsertDTO;
import gr.nifitsas.dealsapp.dto.productDTOs.ProductReadOnlyDTO;

import gr.nifitsas.dealsapp.dto.productDTOs.ProductUpdateDTO;
import gr.nifitsas.dealsapp.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
  private final ProductService productService;

  @GetMapping("/find")
  public ResponseEntity<Optional<ProductReadOnlyDTO>> getProductById(@RequestParam("id") Long id) {
    Optional<ProductReadOnlyDTO> product = productService.findProductById(id);
    try {
      return new ResponseEntity<>(product, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("ERROR: Could not get Product with id: " + id , e);
      throw e;
    }
  }
  @GetMapping("/all")
  public ResponseEntity<Page<ProductReadOnlyDTO>> getPaginatedProducts(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "1") int size) {

    Page<ProductReadOnlyDTO> productsPage = productService.getPaginatedProducts(page, size);
    try {
      System.out.println(page);
      System.out.println(size);
      return new ResponseEntity<>(productsPage, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("ERROR: Could not get Products.", e);
      throw e;
    }
  }
  @GetMapping("/results")
  public ResponseEntity<Paginated<ProductReadOnlyDTO>> getProductsFilteredPaginated(@ModelAttribute ProductFilters filters)
    throws AppObjectInvalidArgumentException {
    System.out.println("requested filters: " + filters);
    try {
      if (filters == null) filters = ProductFilters.builder().build();
      return ResponseEntity.ok( productService.getPaginatedFilteredProducts(filters));
    } catch (Exception e) {
      LOGGER.error("ERROR: Could not get Products.", e);
      throw e;
    }
  }

  @PostMapping("/add")
  public ResponseEntity<ProductReadOnlyDTO> addProduct(
    @RequestPart(name = "category") Long categoryId,
    @RequestPart(name = "store") Long storeId,
    @Valid @RequestPart(name = "product") ProductInsertDTO productInsertDTO,
    @RequestPart("image") MultipartFile image) throws  AppObjectInvalidArgumentException, AppObjectAlreadyExistsException, IOException {
    try {
      ProductReadOnlyDTO productReadOnlyDTO = productService.saveProduct(categoryId, storeId, productInsertDTO, image);
      return new ResponseEntity<>(productReadOnlyDTO, HttpStatus.OK);
    } catch (AppObjectAlreadyExistsException | AppObjectInvalidArgumentException | IOException e) {
      LOGGER.error("Attachment", "image can not get uploaded", e);
      throw e;


    }
  }

  @PutMapping("/update/{productId}")
  public ResponseEntity<ProductReadOnlyDTO> updateProduct(
    @PathVariable("productId") Long productId,
    @RequestParam(name = "category", required = false) Long categoryId,
    @RequestParam(name = "store", required = false) Long storeId,
    @RequestPart(name = "product") @Valid ProductUpdateDTO productUpdateDTO,
    @RequestPart(name = "image", required = false) MultipartFile image)
    throws AppObjectNotFoundException, AppObjectInvalidArgumentException, IOException, AppObjectAlreadyExistsException {

    try {
      ProductReadOnlyDTO updatedProduct = productService.updateProduct(productId, categoryId, storeId, productUpdateDTO, image);
      return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    } catch (AppObjectNotFoundException | AppObjectInvalidArgumentException | IOException |
             AppObjectAlreadyExistsException e) {
      LOGGER.error("Error updating product: {}", e.getMessage());
      throw e;
    }
  }

  @DeleteMapping("/remove/{productId}")
  public ResponseEntity<ProductReadOnlyDTO>deleteStore(@PathVariable("productId")Long productId) throws AppObjectInvalidArgumentException, AppObjectNotFoundException  {
    try{
      ProductReadOnlyDTO deletedProduct = productService.deleteProduct(productId);
      return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
    }catch (AppObjectInvalidArgumentException | AppObjectNotFoundException e){
      LOGGER.error("ERROR: Could not delete Store.", e);
      throw e;
    }
  }

}
