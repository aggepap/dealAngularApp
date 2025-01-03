package gr.nifitsas.dealsapp.controller;

import gr.nifitsas.dealsapp.core.exceptions.*;
import gr.nifitsas.dealsapp.core.filters.Paginated;
import gr.nifitsas.dealsapp.core.filters.ProductFilters;
import gr.nifitsas.dealsapp.dto.productDTOs.ProductInsertDTO;
import gr.nifitsas.dealsapp.dto.productDTOs.ProductReadOnlyDTO;

import gr.nifitsas.dealsapp.dto.productDTOs.ProductUpdateDTO;
import gr.nifitsas.dealsapp.model.static_data.Store;
import gr.nifitsas.dealsapp.service.ProductService;
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
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

  /**
   * Retrieves a product by its ID.
   *
   * @param id The ID of the product to retrieve.
   * @return A ResponseEntity containing an Optional of ProductReadOnlyDTO, or an empty Optional if no product is found.
   * @throws Exception Any other unexpected error that may occur during retrieval.
   */
  //OpenAPI Annotations
  @Operation(summary = "Retrieves product info for a specific product id" )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Product found and info fetched",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = ProductReadOnlyDTO.class)) }),
    @ApiResponse(responseCode = "404", description = "Product not found",
      content = @Content)})
  //Controller
  @GetMapping("/find")
  public ResponseEntity<Optional<ProductReadOnlyDTO>> getProductById(@RequestParam("id") Long id) throws AppObjectNotFoundException {
    Optional<ProductReadOnlyDTO> product = productService.findProductById(id);
    if(product.isPresent()) {
    try {
      return new ResponseEntity<>(product, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("ERROR: Could not get Product with id: {}", id, e);
      throw e;
    }
    }else {
      throw new AppObjectNotFoundException("Product", "Product with id: "+ id + " not found");
    }
  }


  /**
   * Retrieves a paginated list of products filtered by the provided criteria.
   *
   * @param filters The ProductFilters object containing the filtering criteria.
   * @return A ResponseEntity containing a Paginated object with filtered ProductReadOnlyDTO objects and an HTTP status of OK (200).
   * @throws AppObjectInvalidArgumentException If invalid filters are provided.
   * @throws Exception Any other unexpected error that may occur during retrieval.
   */
  //OpenAPI Annotations
  @Operation(summary = "Retrieves a paginated list of products filtered by the provided criteria." )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Products found and info fetched or not found an returns an empty list",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = ProductReadOnlyDTO.class)) }),
    @ApiResponse(responseCode = "400", description = "Invalid Parameters",
      content = @Content)})
  //Controller
  @GetMapping("/results")
  public ResponseEntity<Paginated<ProductReadOnlyDTO>> getProductsFilteredPaginated( @ModelAttribute ProductFilters filters)
    throws AppObjectInvalidArgumentException {

    try {
      if (filters == null) filters = ProductFilters.builder().build();
      return ResponseEntity.ok( productService.getPaginatedFilteredProducts(filters));
    } catch (Exception e) {
      LOGGER.error("ERROR: Could not get Products.", e);
      throw e;
    }
  }

  /**
   * Creates a new product.
   *
   * @param categoryId The ID of the category the product belongs to.
   * @param storeId The ID of the store the product belongs to.
   * @param productInsertDTO The ProductInsertDTO object containing the product data.
   * @param image The MultipartFile containing the product image.
   * @return A ResponseEntity containing the created ProductReadOnlyDTO object and an HTTP status of OK (201).
   * @throws AppObjectAlreadyExistsException If a product with the same name already exists.
   * @throws AppObjectInvalidArgumentException If invalid product data is provided.
   * @throws IOException If an error occurs while saving the product image.
   */
  //OpenAPI Annotations
  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(summary = "Creates a new product." )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Product Created",
         content = { @Content(mediaType = "application/json",
         schema = @Schema(implementation = ProductReadOnlyDTO.class)) }),
    @ApiResponse(responseCode = "400", description = "Invalid Parameters",
      content = @Content),
    @ApiResponse(responseCode = "401", description = "Authentication Error",
      content = @Content),
    @ApiResponse(responseCode = "409", description = "Already Exists",
      content = @Content)})
  //Controller
  @PostMapping(value = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})//consumes needed for OpenAPI
  public ResponseEntity<ProductReadOnlyDTO> addProduct(
    @RequestPart(name = "category") Long categoryId,
    @RequestPart(name = "store") Long storeId,
    @Valid @RequestPart(name = "product") ProductInsertDTO productInsertDTO,
    @RequestPart("image") MultipartFile image) throws  AppObjectInvalidArgumentException, AppObjectAlreadyExistsException, IOException {
    try {
      ProductReadOnlyDTO productReadOnlyDTO = productService.saveProduct(categoryId, storeId, productInsertDTO, image);
      return new ResponseEntity<>(productReadOnlyDTO, HttpStatus.CREATED);
    } catch (AppObjectAlreadyExistsException | AppObjectInvalidArgumentException | IOException e) {
      LOGGER.error("Attachment", "image can not get uploaded", e);
      throw e;


    }
  }

  /**
   * Updates an existing product.
   *
   * @param productId The ID of the product to update.
   * @param categoryId The ID of the category to associate with the product (optional).
   * @param storeId The ID of the store to associate with the product (optional).
   * @param productUpdateDTO The ProductUpdateDTO object containing the updated product data.
   * @param image The MultipartFile containing the updated product image (optional).
   * @return A ResponseEntity containing the updated ProductReadOnlyDTO object and an HTTP status of OK (200).
   * @throws AppObjectNotFoundException If the product to update is not found.
   * @throws AppObjectAlreadyExistsException If a product with the same name already exists.
   * @throws AppObjectInvalidArgumentException If invalid product data is provided.
   * @throws IOException If an error occurs while saving the product image.
   */
  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(summary = "Updates an existing product" )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Product Updated succesfully",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = ProductReadOnlyDTO.class)) }),
    @ApiResponse(responseCode = "400", description = "Invalid Parameters",
      content = @Content),
    @ApiResponse(responseCode = "401", description = "Authentication Error",
      content = @Content),
    @ApiResponse(responseCode = "404", description = "Product Not Found",
      content = @Content),
    @ApiResponse(responseCode = "409", description = "Already Exists",
      content = @Content)})
  //Controller
  @PutMapping(value = "/update/{productId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<ProductReadOnlyDTO> updateProduct(
    @PathVariable("productId") Long productId,
    @RequestParam(name = "categoryId", required = false) Long categoryId,
    @RequestParam(name = "storeId", required = false) Long storeId,
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

  /**
   * Deletes a product.
   *
   * @param productId The ID of the product to delete.
   * @return A ResponseEntity containing the deleted ProductReadOnlyDTO object and an HTTP status of OK (200).
   * @throws AppObjectNotFoundException If the product to delete is not found.
   * @throws AppObjectInvalidArgumentException If an error occurs during deletion.
   */
  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(summary = " Deletes a product" )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Product deleted succesfully",
      content = { @Content(mediaType = "application/json",
        schema = @Schema(implementation = ProductReadOnlyDTO.class)) }),
    @ApiResponse(responseCode = "401", description = "Authentication Error",
      content = @Content),
    @ApiResponse(responseCode = "404", description = "Product Not Found",
      content = @Content)})
  //Controller
  @DeleteMapping("/remove/{productId}")
  public ResponseEntity<ProductReadOnlyDTO>deleteProduct(@PathVariable("productId")Long productId) throws AppObjectInvalidArgumentException, AppObjectNotFoundException  {
    LOGGER.info("Deleting product: {}", productId);

    if(productService.findProductById(productId).isEmpty()) {
      throw new AppObjectNotFoundException("Product", "Product With id :" + productId + " was not found");
    }
    try{
      ProductReadOnlyDTO deletedProduct = productService.deleteProduct(productId);
      return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
    }catch (AppObjectInvalidArgumentException | AppObjectNotFoundException e){
      LOGGER.error("ERROR: Could not delete Store.", e);
      throw e;
    }
  }

}
