package gr.nifitsas.dealsapp.service;

import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.core.mapper.Mapper;
import gr.nifitsas.dealsapp.dto.StoreDTOs.StoreInsertDTO;
import gr.nifitsas.dealsapp.dto.StoreDTOs.StoreReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.StoreDTOs.StoreUpdateDTO;
import gr.nifitsas.dealsapp.model.Attachment;
import gr.nifitsas.dealsapp.model.Product;
import gr.nifitsas.dealsapp.model.static_data.Store;
import gr.nifitsas.dealsapp.repository.ProductRepository;
import gr.nifitsas.dealsapp.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService implements IStoreService {

  private final StoreRepository storeRepository;
  private final Mapper mapper;
  private final ProductRepository productRepository;
  private final AttachmentService attachmentService;


  /**
   * Retrieves all stores.
   *
   * @return A list of all stores as StoreReadOnlyDTO objects.
   */
  @Override
  public List<StoreReadOnlyDTO> findAllStores() {
    return storeRepository.findAll().stream().map(mapper::mapToStoreReadOnlyDTO).collect(Collectors.toList());
  }

  /**
   * Finds all products (deals) associated with a specific store.
   *
   * @param id The ID of the store to find deals for.
   * @return A list of Product entities representing the store's deals.
   */
  @Override
  public List<Product> findAllStoreDeals(Long id) {
   return productRepository.findByStoreId(id);
  }


  /**
   * Finds a store by its ID.
   *
   * @param id The ID of the store to find.
   * @return An Optional containing the store as a StoreReadOnlyDTO object if found, or empty if not found.
   */
  @Override
  public Optional<StoreReadOnlyDTO> findStoreById(Long id) {
    return storeRepository.findById(id).map(mapper::mapToStoreReadOnlyDTO);
  }

  /**
   * Finds a store entity by its ID.
   *
   * @param id The ID of the store to find.
   * @return An Optional containing the store entity, or empty if not found.
   */
  @Override
  public Optional<Store> findStoreEntintyById(Long id) {
    return storeRepository.findStoreByIdIs(id);
  }

  /**
   * Updates an existing store.
   *
   * @param storeId The ID of the store to update.
   * @param storeUpdateDTO A StoreUpdateDTO object containing the updated store information.
   * @param logo A MultipartFile containing the new logo for the store (optional).
   * @return The updated store as a StoreReadOnlyDTO object.
   * @throws AppObjectNotFoundException If the store to update is not found.
   * @throws AppObjectAlreadyExistsException If a store with the same name already exists (excluding the store being updated).
   * @throws AppObjectInvalidArgumentException if data are not complient with updateDTO or if Store is named "Other"
   * @throws IOException If there's an error saving the store logo.
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public StoreReadOnlyDTO updateStore(Long storeId, StoreUpdateDTO storeUpdateDTO , MultipartFile logo) throws AppObjectNotFoundException, AppObjectAlreadyExistsException, IOException, AppObjectInvalidArgumentException {

    Optional<Store> existingStore = storeRepository.findByName(storeUpdateDTO.getName());
    if (existingStore.isPresent() && !existingStore.get().getId().equals(storeId)) {
      throw new AppObjectAlreadyExistsException("Store", "Store with name '" + storeUpdateDTO.getName() + "' already exists.");
    }

    Store selectedStore = storeRepository.findById(storeId)
      .orElseThrow(() -> new AppObjectNotFoundException("Store", "Store with id " + storeId + " not found."));
    if (selectedStore.getName().trim().equals("Other")) {
      throw new AppObjectInvalidArgumentException("Store", "Store 'Other' cannot be updated.");
    }
    selectedStore.setName(storeUpdateDTO.getName());
    selectedStore.setSiteURL(storeUpdateDTO.getSiteURL());
    attachmentService.saveImage(selectedStore, logo);
    Store updatedStore = storeRepository.save(selectedStore);
    return mapper.mapToStoreReadOnlyDTO(updatedStore);
  }


  /**
   * Saves a new store.
   *
   * @param dto A StoreInsertDTO object containing the new store information.
   * @param logo A MultipartFile containing the logo for the new store (optional).
   * @return The saved store as a StoreReadOnlyDTO object.
   * @throws AppObjectAlreadyExistsException If a store with the same name already exists.
   * @throws AppObjectInvalidArgumentException If the provided DTO is invalid.
   * @throws IOException If there's an error saving the store logo.
   */
  @Transactional(rollbackOn = Exception.class)
  @Override
  public StoreReadOnlyDTO saveStore(StoreInsertDTO dto, MultipartFile logo) throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException, IOException {
   if(storeRepository.findByName(dto.getName()).isPresent()){
     throw new AppObjectAlreadyExistsException("Store", "Store named " + dto.getName() + " already exists");
   }
   Store store = mapper.mapToStoreEntity(dto);
   attachmentService.saveImage(store, logo);
   Store savedStore = storeRepository.save(store);
   return mapper.mapToStoreReadOnlyDTO(savedStore);
  }

  /**
   * Deletes a store.If the store to be deleted has products, all it's products
   *    * are moved to "Other" store (created automatically if not already present)
   *
   * @param id The ID of the store to delete.
   * @return The deleted store as a StoreReadOnlyDTO object.
   * @throws AppObjectNotFoundException If the store to delete is not found.
   * @throws AppObjectInvalidArgumentException If the store 'Other' is attempted to be deleted.
   * @throws IOException If there's an error while processing the store deletion.
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public StoreReadOnlyDTO deleteStore(Long id) throws AppObjectNotFoundException, AppObjectInvalidArgumentException {
    Optional<Store> optionalStore = storeRepository.findById(id);

    if(optionalStore.orElseThrow().getName().equals("Other")){
      throw new AppObjectInvalidArgumentException("Store", "Store 'Other' cannot be deleted");
    }

    if (optionalStore.isPresent()) {
      Optional<Store> defaultStore = storeRepository.findByName("Other");

      Store resolvedDefaultStore;
      if (defaultStore.isEmpty()) {
        var defaultStoreDTO = new StoreInsertDTO("Other", "https://localhost4200.com/stores");
         resolvedDefaultStore = mapper.mapToStoreEntity(defaultStoreDTO);
        storeRepository.save(resolvedDefaultStore);
      } else{
        resolvedDefaultStore = defaultStore.get();
    }
      optionalStore.get().getAllStoreProducts().forEach(resolvedDefaultStore::addStoreProduct);
      Store store = optionalStore.get();

      storeRepository.delete(store);
      return mapper.mapToStoreReadOnlyDTO(store);
    } else {
      throw new AppObjectNotFoundException("Store", "Store with id: " + id + " not found");
    }
  }

}
