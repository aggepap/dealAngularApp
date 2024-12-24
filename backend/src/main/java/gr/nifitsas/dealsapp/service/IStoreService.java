package gr.nifitsas.dealsapp.service;


import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.dto.StoreDTOs.StoreInsertDTO;
import gr.nifitsas.dealsapp.dto.StoreDTOs.StoreReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.StoreDTOs.StoreUpdateDTO;
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
  StoreReadOnlyDTO updateStore(Long storeId, StoreUpdateDTO storeUpdateDTO , MultipartFile logo) throws AppObjectNotFoundException, AppObjectAlreadyExistsException, IOException;
  StoreReadOnlyDTO saveStore(StoreInsertDTO dto, MultipartFile logo) throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException;
  StoreReadOnlyDTO deleteStore(Long id) throws AppObjectNotFoundException, AppObjectInvalidArgumentException;

}
