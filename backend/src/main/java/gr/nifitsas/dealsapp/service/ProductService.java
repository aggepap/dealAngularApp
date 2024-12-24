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

  @Override
  public List<ProductReadOnlyDTO> getProducts() {
   return productRepository.findAll().stream().map(mapper::mapToProductReadOnlyDTO).collect(Collectors.toList());
  }

  @Override
  public Optional<ProductReadOnlyDTO> findProductById(Long id) {
    return productRepository.findById(id).map(mapper::mapToProductReadOnlyDTO);
  }

  @Override
  public Page<ProductReadOnlyDTO> getPaginatedProducts(int page, int size) {
   Pageable pageable = PageRequest.of(page, size);
   Page<Product> products = productRepository.findAll(pageable);
   return products.map(mapper::mapToProductReadOnlyDTO);
  }

  @Override
  public Paginated<ProductReadOnlyDTO> getPaginatedFilteredProducts(ProductFilters filters) throws AppObjectInvalidArgumentException {
    var filtered = productRepository.findAll(getSpecsFromFilters(filters), filters.getPageable());
    return new Paginated<>(filtered.map(mapper::mapToProductReadOnlyDTO));
  }

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


  @Override
  @Transactional(rollbackOn = Exception.class)
  public ProductReadOnlyDTO deleteProduct(Long id) throws AppObjectNotFoundException, AppObjectInvalidArgumentException {
    Optional<Product> optionalProduct = productRepository.findById(id);
    if (optionalProduct.isPresent()) {
      Product product = optionalProduct.get();
      productRepository.delete(product);
      return mapper.mapToProductReadOnlyDTO(product);
    } else {
      throw new AppObjectNotFoundException("Product", "Product with id: " + id + " not found");
    }
  }



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
