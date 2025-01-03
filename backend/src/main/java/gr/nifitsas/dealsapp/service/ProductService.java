package gr.nifitsas.dealsapp.service;

import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;

import gr.nifitsas.dealsapp.core.filters.Paginated;
import gr.nifitsas.dealsapp.core.filters.ProductFilters;
import gr.nifitsas.dealsapp.core.mapper.Mapper;
import gr.nifitsas.dealsapp.dto.productDTOs.ProductInsertDTO;
import gr.nifitsas.dealsapp.dto.productDTOs.ProductReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.productDTOs.ProductUpdateDTO;
import gr.nifitsas.dealsapp.model.Product;
import gr.nifitsas.dealsapp.model.static_data.Category;
import gr.nifitsas.dealsapp.model.static_data.Store;
import gr.nifitsas.dealsapp.repository.ProductRepository;
import gr.nifitsas.dealsapp.service.specifications.ProductSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
  private final CategoryService categoryService;
  private final ProductRepository productRepository;
  private final AttachmentService attachmentService;
  private final StoreService storeService;
  private final Mapper mapper;
  private final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

  /**
   * Retrieves all products as ProductReadOnlyDTO objects.
   *
   * @return A list of all products.
   */
  @Override
  public List<ProductReadOnlyDTO> getProducts() {
   return productRepository.findAll().stream().map(mapper::mapToProductReadOnlyDTO).collect(Collectors.toList());
  }


  /**
   * Finds a product by its ID.
   *
   * @param id The ID of the product to find.
   * @return An Optional containing the product as a ProductReadOnlyDTO object if found, or empty if not found.
   */
  @Override
  public Optional<ProductReadOnlyDTO> findProductById(Long id) {
    return productRepository.findById(id).map(mapper::mapToProductReadOnlyDTO);
  }

  /**
   * Retrieves a paginated list of products.
   *
   * @param page The page number (starting from 0).
   * @param size The number of products per page.
   * @return A Page object containing ProductReadOnlyDTO objects for the requested page.
   */
  @Override
  public Page<ProductReadOnlyDTO> getPaginatedProducts(int page, int size) {
   Pageable pageable = PageRequest.of(page, size);
   Page<Product> products = productRepository.findAll(pageable);
   return products.map(mapper::mapToProductReadOnlyDTO);
  }

  /**
   * Retrieves a paginated list of products filtered by the provided criteria.
   *
   * @param filters A ProductFilters object containing the filtering criteria.
   * @return A Paginated object containing filtered ProductReadOnlyDTO objects.
   * @throws AppObjectInvalidArgumentException If invalid filters are provided.
   */
  @Override
  public Paginated<ProductReadOnlyDTO> getPaginatedFilteredProducts(ProductFilters filters) throws AppObjectInvalidArgumentException {
    var filtered = productRepository.findAll(getSpecsFromFilters(filters), filters.getPageable());
    return new Paginated<>(filtered.map(mapper::mapToProductReadOnlyDTO));
  }

  /**
   * Saves a new product.
   *
   * @param categoryId The ID of the category to associate with the product.
   * @param storeId The ID of the store to associate with the product.
   * @param dto A ProductInsertDTO object containing the product information.
   * @param image A MultipartFile containing the product image (optional).
   * @return The saved product as a ProductReadOnlyDTO object.
   * @throws AppObjectAlreadyExistsException If a product with the same name already exists.
   * @throws AppObjectInvalidArgumentException If invalid product data is provided.
   * @throws IOException If there's an error saving the product image.
   */
  @Override
  @Transactional
  public ProductReadOnlyDTO saveProduct( Long categoryId, Long storeId, ProductInsertDTO dto, MultipartFile image) throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException {
   if(productRepository.findByName(dto.getName()).isPresent()){
     throw new AppObjectAlreadyExistsException("Product","Product with Name " +dto.getName() + " already exists");
   }

   Product product = mapper.mapToProductEntity(dto);
   attachmentService.saveImage(product, image);

   Product savedProduct = productRepository.save(product);
   Optional<Category> category = categoryService.findCategoryById(categoryId);
   Optional<Store> store = storeService.findStoreEntintyById(storeId);
    category.ifPresent(product::setCategory);
    store.ifPresent(product::setStore);

   return mapper.mapToProductReadOnlyDTO(savedProduct);
  }

  /**
   * Updates an existing product.
   *
   * @param productId The ID of the product to update.
   * @param categoryId The ID of the category to associate with the product (optional).
   * @param storeId The ID of the store to associate with the product (optional).
   * @param dto A ProductUpdateDTO object containing the updated product information.
   * @param image A MultipartFile containing the updated product image (optional).
   * @return The updated product as a ProductReadOnlyDTO object.
   * @throws AppObjectNotFoundException If the product to update is not found.
   * @throws AppObjectAlreadyExistsException If a product with the same name already exists (excluding the product being updated).
   * @throws AppObjectInvalidArgumentException If invalid product data is provided.
   * @throws IOException If there's an error saving the product image.
   */
  @Override
  @Transactional
  public ProductReadOnlyDTO updateProduct(
    Long productId,
    Long categoryId,
    Long storeId,
    ProductUpdateDTO dto,
    MultipartFile image
  ) throws AppObjectNotFoundException, AppObjectInvalidArgumentException, IOException, AppObjectAlreadyExistsException {

    // Fetch existing product by ID
    Product product = productRepository.findById(productId)
      .orElseThrow(() -> new AppObjectNotFoundException("Product", "Product with ID " + productId + " not found"));

    // Update product fields from the DTO
    if (!product.getName().equals(dto.getName())) {
      // Check for duplicate product name
      if (productRepository.findByName(dto.getName()).isPresent()) {
        throw new AppObjectAlreadyExistsException("Product", "Product with Name " + dto.getName() + " already exists");
      }
      product.setName(dto.getName());
    }

    product.setDescription(dto.getDescription());
    product.setPrice(dto.getPrice());
    product.setURL(dto.getUrl());
    product.setCoupon(dto.getCoupon());

    // Update image if provided
    if (image != null && !image.isEmpty()) {
      attachmentService.saveImage(product, image);
    }

    // Update category if provided
    if (categoryId != null) {
      Category category = categoryService.findCategoryById(categoryId)
        .orElseThrow(() -> new AppObjectNotFoundException("Category", "Category with ID " + categoryId + " not found"));
      product.setCategory(category);
    }

    // Update store if provided
    if (storeId != null) {
      Store store = storeService.findStoreEntintyById(storeId)
        .orElseThrow(() -> new AppObjectNotFoundException("Store", "Store with ID " + storeId + " not found"));
      product.setStore(store);
    }

    // Save the updated product to the repository
    Product updatedProduct = productRepository.save(product);

    // Map and return the updated product DTO
    return mapper.mapToProductReadOnlyDTO(updatedProduct);
  }


  /**
   * Deletes a product.
   *
   * @param id The ID of the product to delete.
   * @return The deleted product as a ProductReadOnlyDTO object.
   * @throws AppObjectNotFoundException If the product to delete is not found.
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public ProductReadOnlyDTO deleteProduct(Long id) throws AppObjectNotFoundException, AppObjectInvalidArgumentException {
    System.out.println("/////////////////////////////////////////////////////////");
    System.out.println("/////////////////////////////////////////////////////////");
    System.out.println("/////////////////////////////////////////////////////////");
    System.out.println("/////////////////////////////////////////////////////////");
    System.out.println(id);
    Optional<Product> optionalProduct = productRepository.findById(id);

    if (optionalProduct.isEmpty()) {
    System.out.println(optionalProduct.isPresent());
    if (optionalProduct.isPresent()) {
      Product product = optionalProduct.get();

      productRepository.delete(product);
      return mapper.mapToProductReadOnlyDTO(product);
    } else {
      throw new AppObjectNotFoundException("Product", "Product with id: " + id + " not found");
    }
    Product product = optionalProduct.get();
    LOGGER.info(product.getName(), product.getId());
    productRepository.delete(product);
    return mapper.mapToProductReadOnlyDTO(product);
  }

  /**
   * Creates a Specification object for filtering products based on the provided filters.
   *
   * @param filters The ProductFilters object containing the filtering criteria.
   * @return A Specification object for filtering products.
   */
  private Specification<Product> getSpecsFromFilters(ProductFilters filters) {
    Specification<Product> filter = Specification.where(ProductSpecification.productTitleIsLike("name", filters.getName()));
    if (filters.getCategoryId() != null) {
      filter = filter.and(ProductSpecification.productCategoryIs(filters.getCategoryId()));
    }
    if (filters.getStoreId() != null) {
      filter = filter.and(ProductSpecification.productStoreIs(filters.getStoreId()));
    }
    return filter;
  }
}
