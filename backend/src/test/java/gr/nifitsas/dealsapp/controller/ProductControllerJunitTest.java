//package gr.nifitsas.dealsapp.controller;
//import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExistsException;
//import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
//import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
//import gr.nifitsas.dealsapp.core.filters.Paginated;
//import gr.nifitsas.dealsapp.core.filters.ProductFilters;
//import gr.nifitsas.dealsapp.dto.productDTOs.ProductInsertDTO;
//import gr.nifitsas.dealsapp.dto.productDTOs.ProductReadOnlyDTO;
//import gr.nifitsas.dealsapp.dto.productDTOs.ProductUpdateDTO;
//import gr.nifitsas.dealsapp.service.ProductService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.Optional;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ProductControllerJunitTest {
//
//  @Mock
//  private ProductService productService;
//
//  private ProductController productController;
//
//  @BeforeEach
//  public void setUp() {
//    productController = new ProductController(productService);
//  }
//
//  // Test get product by id - Success (Product Found)
//  @Test
//  public void testGetProductById_success() throws AppObjectNotFoundException {
//    Long id = 1L;
//    Optional<ProductReadOnlyDTO> product = Optional.of(new ProductReadOnlyDTO());
//    when(productService.findProductById(id)).thenReturn(product);
//
//    ResponseEntity<Optional<ProductReadOnlyDTO>> response = productController.getProductById(id);
//
//    assertEquals(HttpStatus.OK, response.getStatusCode());
//    verify(productService, times(1)).findProductById(id);
//  }
//
//  // Test get product by id - Not Found
//  @Test
//  public void testGetProductById_notFound() throws AppObjectNotFoundException {
//    Long id = 1L;
//    when(productService.findProductById(id)).thenReturn(Optional.empty());
//
//    assertThrows(AppObjectNotFoundException.class, () -> productController.getProductById(id));
//    verify(productService, times(1)).findProductById(id);
//  }
//
//  // Test get product by id - Service throws exception
//  @Test
//  public void testGetProductById_serviceThrowsException() throws AppObjectNotFoundException {
//    Long id = 1L;
//    when(productService.findProductById(id)).thenThrow(new RuntimeException("Test Exception"));
//
//    assertThrows(RuntimeException.class, () -> productController.getProductById(id));
//    verify(productService, times(1)).findProductById(id);
//  }
//
//  // Test get products with filters - Service throws exception
//  @Test
//  public void testGetProductsFilteredPaginated_serviceThrowsException() throws AppObjectInvalidArgumentException {
//    ProductFilters filters = new ProductFilters();
//    when(productService.getPaginatedFilteredProducts(filters)).thenThrow(new RuntimeException("Test Exception"));
//
//    assertThrows(RuntimeException.class, () -> productController.getProductsFilteredPaginated(filters));
//    verify(productService, times(1)).getPaginatedFilteredProducts(filters);
//  }
//
//  // Test add product - Success
//  @Test
//  public void testAddProduct_success() throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException {
//    Long categoryId = 1L;
//    Long storeId = 1L;
//    ProductInsertDTO productInsertDTO = new ProductInsertDTO();
//    MultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image data".getBytes());
//    ProductReadOnlyDTO createdProduct = new ProductReadOnlyDTO();
//    when(productService.saveProduct(categoryId, storeId, productInsertDTO, image)).thenReturn(createdProduct);
//
//    ResponseEntity<ProductReadOnlyDTO> response = productController.addProduct(categoryId, storeId, productInsertDTO, image);
//
//    assertEquals(HttpStatus.CREATED, response.getStatusCode());
//    assertEquals(createdProduct, response.getBody());
//    verify(productService, times(1)).saveProduct(categoryId, storeId, productInsertDTO, image);
//  }
//
//  // Test add product - Service throws AppObjectAlreadyExistsException
//  @Test
//  public void testAddProduct_throwsAppObjectAlreadyExistsException() throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException {
//    Long categoryId = 1L;
//    Long storeId = 1L;
//    ProductInsertDTO productInsertDTO = new ProductInsertDTO();
//    MultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image data".getBytes());
//    when(productService.saveProduct(categoryId, storeId, productInsertDTO, image)).thenThrow(new AppObjectAlreadyExistsException("Product", "Product already exists"));
//
//    assertThrows(AppObjectAlreadyExistsException.class, () -> productController.addProduct(categoryId, storeId, productInsertDTO, image));
//    verify(productService, times(1)).saveProduct(categoryId, storeId, productInsertDTO, image);
//  }
//
//  // Test add product - Service throws AppObjectInvalidArgumentException
//  @Test
//  public void testAddProduct_throwsAppObjectInvalidArgumentException() throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException {
//    Long categoryId = 1L;
//    Long storeId = 1L;
//    ProductInsertDTO productInsertDTO = new ProductInsertDTO();
//    MultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image data".getBytes());
//    when(productService.saveProduct(categoryId, storeId, productInsertDTO, image)).thenThrow(new AppObjectInvalidArgumentException("Product", "Invalid product data"));
//
//    assertThrows(AppObjectInvalidArgumentException.class, () -> productController.addProduct(categoryId, storeId, productInsertDTO, image));
//    verify(productService, times(1)).saveProduct(categoryId, storeId, productInsertDTO, image);
//  }
//
//  // Test add product - Service throws IOException
//  @Test
//  public void testAddProduct_throwsIOException() throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException {
//    Long categoryId = 1L;
//    Long storeId = 1L;
//    ProductInsertDTO productInsertDTO = new ProductInsertDTO();
//    MultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image data".getBytes());
//    when(productService.saveProduct(categoryId, storeId, productInsertDTO, image)).thenThrow(new IOException("Image upload failed"));
//
//    assertThrows(IOException.class, () -> productController.addProduct(categoryId, storeId, productInsertDTO, image));
//    verify(productService, times(1)).saveProduct(categoryId, storeId, productInsertDTO, image);
//  }
//
//  // Test update product - Success
//  @Test
//  public void testUpdateProduct_success() throws AppObjectNotFoundException, AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException {
//    Long productId = 1L;
//    Long categoryId = 1L;
//    Long storeId = 1L;
//    ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO();
//    MultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image data".getBytes());
//    ProductReadOnlyDTO updatedProduct = new ProductReadOnlyDTO();
//    when(productService.updateProduct(productId, categoryId, storeId, productUpdateDTO, image)).thenReturn(updatedProduct);
//
//    ResponseEntity<ProductReadOnlyDTO> response = productController.updateProduct(productId, categoryId, storeId, productUpdateDTO, image);
//
//    assertEquals(HttpStatus.OK, response.getStatusCode());
//    assertEquals(updatedProduct, response.getBody());
//    verify(productService, times(1)).updateProduct(productId, categoryId, storeId, productUpdateDTO, image);
//  }
//
//  // Test update product - Service throws AppObjectNotFoundException
//  @Test
//  public void testUpdateProduct_throwsAppObjectNotFoundException() throws AppObjectNotFoundException, AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException {
//    Long productId = 1L;
//    Long categoryId = 1L;
//    Long storeId = 1L;
//    ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO();
//    MultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image data".getBytes());
//    when(productService.updateProduct(productId, categoryId, storeId, productUpdateDTO, image)).thenThrow(new AppObjectNotFoundException("Product", "Product not found"));
//
//    assertThrows(AppObjectNotFoundException.class, () -> productController.updateProduct(productId, categoryId, storeId, productUpdateDTO, image));
//    verify(productService, times(1)).updateProduct(productId, categoryId, storeId, productUpdateDTO, image);
//  }
//
//  // Test update product - Service throws AppObjectAlreadyExistsException
//  @Test
//  public void testUpdateProduct_throwsAppObjectAlreadyExistsException() throws AppObjectNotFoundException, AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException {
//    Long productId = 1L;
//    Long categoryId = 1L;
//    Long storeId = 1L;
//    ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO();
//    MultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image data".getBytes());
//    when(productService.updateProduct(productId, categoryId, storeId, productUpdateDTO, image)).thenThrow(new AppObjectAlreadyExistsException("Product", "Product already exists"));
//
//    assertThrows(AppObjectAlreadyExistsException.class, () -> productController.updateProduct(productId, categoryId, storeId, productUpdateDTO, image));
//    verify(productService, times(1)).updateProduct(productId, categoryId, storeId, productUpdateDTO, image);
//  }
//
//  // Test update product - Service throws AppObjectInvalidArgumentException
//  @Test
//  public void testUpdateProduct_throwsAppObjectInvalidArgumentException() throws AppObjectNotFoundException, AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException {
//    Long productId = 1L;
//    Long categoryId = 1L;
//    Long storeId = 1L;
//    ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO();
//    MultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image data".getBytes());
//    when(productService.updateProduct(productId, categoryId, storeId, productUpdateDTO, image)).thenThrow(new AppObjectInvalidArgumentException("Product", "Invalid product data"));
//
//    assertThrows(AppObjectInvalidArgumentException.class, () -> productController.updateProduct(productId, categoryId, storeId, productUpdateDTO, image));
//    verify(productService, times(1)).updateProduct(productId, categoryId, storeId, productUpdateDTO, image);
//  }
//
//  // Test update product - Service throws IOException
//  @Test
//  public void testUpdateProduct_throwsIOException() throws AppObjectNotFoundException, AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException {
//    Long productId = 1L;
//    Long categoryId = 1L;
//    Long storeId = 1L;
//    ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO();
//    MultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image data".getBytes());
//    when(productService.updateProduct(productId, categoryId, storeId, productUpdateDTO, image)).thenThrow(new IOException("Image upload failed"));
//
//    assertThrows(IOException.class, () -> productController.updateProduct(productId, categoryId, storeId, productUpdateDTO, image));
//    verify(productService, times(1)).updateProduct(productId, categoryId, storeId, productUpdateDTO, image);
//  }
//
//  // Test delete product - Success
//  @Test
//  public void testDeleteProduct_success() throws AppObjectNotFoundException, AppObjectInvalidArgumentException {
//    Long productId = 1L;
//    ProductReadOnlyDTO deletedProduct = new ProductReadOnlyDTO();
//    when(productService.deleteProduct(productId)).thenReturn(deletedProduct);
//
//    ResponseEntity<ProductReadOnlyDTO> response = productController.deleteStore(productId);
//
//    assertEquals(HttpStatus.OK, response.getStatusCode());
//    assertEquals(deletedProduct, response.getBody());
//    verify(productService, times(1)).deleteProduct(productId);
//  }
//
//  // Test delete product - Service throws AppObjectNotFoundException
//  @Test
//  public void testDeleteProduct_throwsAppObjectNotFoundException() throws AppObjectNotFoundException, AppObjectInvalidArgumentException {
//    Long productId = 1L;
//    when(productService.deleteProduct(productId)).thenThrow(new AppObjectNotFoundException("Product", "Product not found"));
//
//    assertThrows(AppObjectNotFoundException.class, () -> productController.deleteStore(productId));
//    verify(productService, times(1)).deleteProduct(productId);
//  }
//
//  // Test delete product - Service throws AppObjectInvalidArgumentException
//  @Test
//  public void testDeleteProduct_throwsAppObjectInvalidArgumentException() throws AppObjectNotFoundException, AppObjectInvalidArgumentException {
//    Long productId = 1L;
//    when(productService.deleteProduct(productId)).thenThrow(new AppObjectInvalidArgumentException("Product", "Invalid product data"));
//
//    assertThrows(AppObjectInvalidArgumentException.class, () -> productController.deleteStore(productId));
//    verify(productService, times(1)).deleteProduct(productId);
//  }
//}
//
