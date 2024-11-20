package gr.nifitsas.dealsapp.service;

import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExists;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.core.mapper.Mapper;
import gr.nifitsas.dealsapp.dto.StoreInsertDTO;
import gr.nifitsas.dealsapp.model.static_data.Store;
import gr.nifitsas.dealsapp.repository.CategoryRepository;
import gr.nifitsas.dealsapp.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService implements IStoreService {
  private final StoreRepository storeRepository;
  private final Mapper mapper;
  private final CategoryRepository categoryRepository;

  @Override
  public List<Store> findAllStores() {
  return storeRepository.findAll();
  }
  @Transactional(rollbackOn = Exception.class)
  @Override
  public Store saveStore(StoreInsertDTO dto) throws AppObjectAlreadyExists, AppObjectInvalidArgumentException {
   if(storeRepository.findByName(dto.getName()).isPresent()){
     throw new AppObjectAlreadyExists("Store", "Store named " + dto.getName() + " already exists");
   }
   Store store = mapper.mapToStoreEntity(dto);
   return storeRepository.save(store);
  }

  @Override
  public String deleteStore(Long id) throws AppObjectNotFoundException, AppObjectInvalidArgumentException {
    if(storeRepository.findById(id).isEmpty()){
      throw new AppObjectNotFoundException("Store", "Store id " + id + " not found");
    }
    String deletedStoreName = storeRepository.findById(id).get().getName();
    categoryRepository.deleteById(id);
    return deletedStoreName;
  }
}
