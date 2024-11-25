package gr.nifitsas.dealsapp.service;


import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExists;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.dto.ProductInsertDTO;
import gr.nifitsas.dealsapp.dto.ProductRealOnlyDTO;
import gr.nifitsas.dealsapp.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;


public interface IProductService {
public Page<ProductRealOnlyDTO> getPaginatedProducts(int page, int size);
ProductRealOnlyDTO saveProduct(ProductInsertDTO dto) throws AppObjectAlreadyExists, AppObjectInvalidArgumentException;
public List<ProductRealOnlyDTO> getFilteredProductsOnSearch(String search) throws AppObjectInvalidArgumentException;
public String deleteProduct(Long id) throws AppObjectNotFoundException;
}
