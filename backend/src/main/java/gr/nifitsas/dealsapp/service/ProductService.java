package gr.nifitsas.dealsapp.service;

import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExists;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;

import gr.nifitsas.dealsapp.core.mapper.Mapper;
import gr.nifitsas.dealsapp.dto.ProductInsertDTO;
import gr.nifitsas.dealsapp.dto.ProductRealOnlyDTO;
import gr.nifitsas.dealsapp.model.Product;
import gr.nifitsas.dealsapp.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
  private final ProductRepository productRepository;
  private final Mapper mapper;

  @Override
  public Page<ProductRealOnlyDTO> getPaginatedProducts(int page, int size) {
   Pageable pageable = PageRequest.of(page, size);
   Page<Product> products = productRepository.findAll(pageable);
   return products.map(mapper::mapToProductReadOnlyDTO);
  }

  @Override
  @Transactional
  public ProductRealOnlyDTO saveProduct(ProductInsertDTO dto) throws AppObjectAlreadyExists, AppObjectInvalidArgumentException {
   if(productRepository.findBySku(dto.getSku()).isPresent()){
     throw new AppObjectAlreadyExists("Product","Product with SKU " +dto.getSku() + " already exists");
   }
   Product product = mapper.mapToProductEntity(dto);
   Product savedProduct = productRepository.save(product);
   return mapper.mapToProductReadOnlyDTO(savedProduct);
  }

  @Override
  public List<ProductRealOnlyDTO> getFilteredProductsOnSearch(String search) throws AppObjectInvalidArgumentException {
    return productRepository.findByNameContaining(search);
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
}
