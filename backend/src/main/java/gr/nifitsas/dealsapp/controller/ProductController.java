package gr.nifitsas.dealsapp.controller;

import gr.nifitsas.dealsapp.core.exceptions.*;
import gr.nifitsas.dealsapp.core.filters.Paginated;
import gr.nifitsas.dealsapp.core.filters.ProductFilters;
import gr.nifitsas.dealsapp.dto.ProductInsertDTO;
import gr.nifitsas.dealsapp.dto.ProductReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.StoreReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.productDTOs.ProductFilterPaginatedDTO;
import gr.nifitsas.dealsapp.service.ProductService;
import gr.nifitsas.dealsapp.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
  private final ProductService productService;

//  @GetMapping("")
//  public ResponseEntity<List<ProductReadOnlyDTO>> getAllProducts() {
//    List<ProductReadOnlyDTO> productList = productService.getProducts();
//    try {
//      return new ResponseEntity<>(productList, HttpStatus.OK);
//    } catch (Exception e) {
//      LOGGER.error("ERROR: Could not get Products.", e);
//      throw e;
//    }
//  }

  @GetMapping("")
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
  public ResponseEntity<Paginated<ProductReadOnlyDTO>> getProductsFilteredPaginated(@Nullable @RequestBody ProductFilters filters)
    throws AppObjectInvalidArgumentException {
    try {
      if (filters == null) filters = ProductFilters.builder().build();
      return ResponseEntity.ok(productService.getPaginatedFilteredProducts(filters));
    } catch (Exception e) {
      LOGGER.error("ERROR: Could not get teachers.", e);
      throw e;
    }
  }

  @PostMapping("/add")
  public ResponseEntity<ProductReadOnlyDTO> addProduct(
    @RequestPart(name = "category") Long categoryId,
    @Valid @RequestPart(name = "product") ProductInsertDTO productInsertDTO,
    @RequestPart("image") MultipartFile image) throws AppServerException, AppObjectInvalidArgumentException, AppObjectAlreadyExists, IOException {
    try {
      ProductReadOnlyDTO productReadOnlyDTO = productService.saveProduct(categoryId, productInsertDTO, image);
      return new ResponseEntity<>(productReadOnlyDTO, HttpStatus.OK);
    } catch (AppObjectAlreadyExists | AppObjectInvalidArgumentException | IOException e) {
      LOGGER.error("Attachment", "image can not get uploaded", e);
      throw e;


    }
  }
}
