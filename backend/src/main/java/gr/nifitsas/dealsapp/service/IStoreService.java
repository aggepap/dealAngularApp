package gr.nifitsas.dealsapp.service;


import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExists;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.dto.ProductReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.StoreInsertDTO;
import gr.nifitsas.dealsapp.dto.StoreReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.StoreUpdateDTO;
import gr.nifitsas.dealsapp.model.Product;
import gr.nifitsas.dealsapp.model.static_data.Store;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IStoreService {
  List<StoreReadOnlyDTO> findAllStores();
  List<Product> findAllStoreDeals(Long id);
  Optional<StoreReadOnlyDTO> findStoreById(Long id);
  Optional<Store> findStoreEntintyById(Long id);
  StoreReadOnlyDTO updateStore(StoreUpdateDTO storeUpdateDTO) throws AppObjectNotFoundException, AppObjectAlreadyExists   ;
  StoreReadOnlyDTO saveStore(StoreInsertDTO dto) throws AppObjectAlreadyExists, AppObjectInvalidArgumentException;
  StoreReadOnlyDTO deleteStore(Long id) throws AppObjectNotFoundException, AppObjectInvalidArgumentException;

}
