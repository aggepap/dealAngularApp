package gr.nifitsas.dealsapp.service;

import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExists;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;

import gr.nifitsas.dealsapp.core.filters.Paginated;
import gr.nifitsas.dealsapp.core.filters.ProductFilters;
import gr.nifitsas.dealsapp.core.mapper.Mapper;
import gr.nifitsas.dealsapp.dto.ProductInsertDTO;
import gr.nifitsas.dealsapp.dto.ProductReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.StoreReadOnlyDTO;
import gr.nifitsas.dealsapp.model.Attachment;
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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
  private final CategoryService categoryService;
  private final ProductRepository productRepository;
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
  public ProductReadOnlyDTO saveProduct( Long categoryId, Long storeId, ProductInsertDTO dto, MultipartFile image) throws AppObjectAlreadyExists, AppObjectInvalidArgumentException, IOException {
   if(productRepository.findByName(dto.getName()).isPresent()){
     throw new AppObjectAlreadyExists("Product","Product with Name " +dto.getName() + " already exists");
   }
   Product product = mapper.mapToProductEntity(dto);
   saveImage(product, image);
   Product savedProduct = productRepository.save(product);
   Optional<Category> category = categoryService.findCategoryById(categoryId);
   Optional<Store> store = storeService.findStoreEntintyById(storeId);
    category.ifPresent(product::setCategory);
    store.ifPresent(product::setStore);

   return mapper.mapToProductReadOnlyDTO(savedProduct);
  }



  @Override
  public String deleteProduct(Long id) throws AppObjectNotFoundException {
   if(productRepository.findById(id).isEmpty()){
     throw new AppObjectNotFoundException("Product","Product with id " + id + " not found");
   }
   String deletedProductName = productRepository.findById(id).get().getName();
   productRepository.deleteById(id);
   return deletedProductName;
  }


  @Transactional(rollbackOn = Exception.class)
  public void saveImage(Product product, MultipartFile image) throws IOException {

    if (image != null && !image.isEmpty()) {

      String originalFileName = image.getOriginalFilename();
      String savedName = UUID.randomUUID() + getFileExtension(originalFileName);
      String uploadDir = "./uploads/";
      Path filepath = Paths.get(uploadDir + savedName);
      Files.createDirectories(filepath.getParent());
      Files.write(filepath, image.getBytes());

      Attachment attachment = new Attachment();
      attachment.setFilename(originalFileName);
      attachment.setSavedName(savedName);
      attachment.setFilePath(filepath.toString());
      attachment.setContentType(image.getContentType());
      attachment.setExtension(getFileExtension(originalFileName));

      product.setImage(attachment);
    }

  }
  private String getFileExtension(String filename) {
    if (filename != null && filename.contains(".")) {
      return filename.substring(filename.lastIndexOf("."));
    }
    return "";
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
