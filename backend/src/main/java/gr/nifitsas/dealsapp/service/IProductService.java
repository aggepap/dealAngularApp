package gr.nifitsas.dealsapp.service;


import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.core.filters.Paginated;
import gr.nifitsas.dealsapp.core.filters.ProductFilters;
import gr.nifitsas.dealsapp.dto.productDTOs.ProductInsertDTO;
import gr.nifitsas.dealsapp.dto.productDTOs.ProductReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.productDTOs.ProductUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface IProductService {
public List<ProductReadOnlyDTO> getProducts();
Optional<ProductReadOnlyDTO> findProductById(Long id);
public Page<ProductReadOnlyDTO> getPaginatedProducts(int page, int size);
ProductReadOnlyDTO saveProduct( Long categoryId, Long storeId, ProductInsertDTO dto, MultipartFile image) throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException;
public ProductReadOnlyDTO updateProduct(Long productId, Long categoryId, Long storeId, ProductUpdateDTO dto, MultipartFile image
) throws AppObjectNotFoundException, AppObjectInvalidArgumentException, IOException, AppObjectAlreadyExistsException;
public Paginated<ProductReadOnlyDTO> getPaginatedFilteredProducts(ProductFilters filters) throws AppObjectInvalidArgumentException;
public ProductReadOnlyDTO deleteProduct(Long id) throws AppObjectNotFoundException, AppObjectInvalidArgumentException;
}
