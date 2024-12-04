package gr.nifitsas.dealsapp.controller;

import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExists;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppServerException;
import gr.nifitsas.dealsapp.dto.ProductInsertDTO;
import gr.nifitsas.dealsapp.dto.ProductReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.StoreReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.categoryDTOs.CategoryReadOnlyDTO;
import gr.nifitsas.dealsapp.service.ProductService;
import gr.nifitsas.dealsapp.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
  private final ProductService productService;

  @GetMapping("")
  public ResponseEntity<List<ProductReadOnlyDTO>> getAllProducts() {
    List<ProductReadOnlyDTO> productList = productService.getProducts();
    try {
      return new ResponseEntity<>(productList, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("ERROR: Could not get Products.", e);
      throw e;
    }
  }

  @PostMapping("/add")
  public ResponseEntity<ProductReadOnlyDTO> addProduct(
    @Valid @RequestPart(name = "product") ProductInsertDTO productInsertDTO,
    @RequestPart("image") MultipartFile image) throws AppServerException {
    try {
      ProductReadOnlyDTO productReadOnlyDTO = productService.saveProduct(productInsertDTO, image);
      return new ResponseEntity<>(productReadOnlyDTO, HttpStatus.OK);
    } catch (AppObjectAlreadyExists | AppObjectInvalidArgumentException | IOException e) {
      throw new AppServerException("Attachment", "image can not get uploaded");
    }
  }
}
