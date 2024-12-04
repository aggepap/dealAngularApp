package gr.nifitsas.dealsapp.service;


import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExists;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.dto.ProductInsertDTO;
import gr.nifitsas.dealsapp.dto.ProductReadOnlyDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface IProductService {
public List<ProductReadOnlyDTO> getProducts();
public Page<ProductReadOnlyDTO> getPaginatedProducts(int page, int size);
ProductReadOnlyDTO saveProduct(ProductInsertDTO dto, MultipartFile image) throws AppObjectAlreadyExists, AppObjectInvalidArgumentException, IOException;
public List<ProductReadOnlyDTO> getFilteredProductsOnSearch(String search) throws AppObjectInvalidArgumentException;
public String deleteProduct(Long id) throws AppObjectNotFoundException;
}
