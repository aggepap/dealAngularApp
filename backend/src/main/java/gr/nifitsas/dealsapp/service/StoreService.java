package gr.nifitsas.dealsapp.service;

import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExists;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.core.mapper.Mapper;
import gr.nifitsas.dealsapp.dto.StoreInsertDTO;
import gr.nifitsas.dealsapp.dto.StoreReadOnlyDTO;
import gr.nifitsas.dealsapp.dto.StoreUpdateDTO;
import gr.nifitsas.dealsapp.model.Attachment;
import gr.nifitsas.dealsapp.model.static_data.Store;
import gr.nifitsas.dealsapp.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
public class StoreService implements IStoreService {
  private final StoreRepository storeRepository;
  private final Mapper mapper;


  @Override
  public List<StoreReadOnlyDTO> findAllStores() {
    return storeRepository.findAll().stream().map(mapper::mapToStoreReadOnlyDTO).collect(Collectors.toList());
  }

  @Override
  public Optional<StoreReadOnlyDTO> findStoreById(long id) {
    return storeRepository.findById(id).map(mapper::mapToStoreReadOnlyDTO);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public StoreReadOnlyDTO updateStore(StoreUpdateDTO storeUpdateDTO) throws AppObjectNotFoundException, AppObjectAlreadyExists  {
    Optional<Store> existingStore = storeRepository.findByName(storeUpdateDTO.getName());
    if (existingStore.isPresent() && !existingStore.get().getId().equals(storeUpdateDTO.getId())) {
      throw new AppObjectAlreadyExists("Store", "Store with name '" + storeUpdateDTO.getName() + "' already exists.");
    }

    Store selectedStore = storeRepository.findById(storeUpdateDTO.getId())
      .orElseThrow(() -> new AppObjectNotFoundException("Store", "Store with id " + storeUpdateDTO.getId() + " not found."));
    selectedStore.setName(storeUpdateDTO.getName());
    selectedStore.setSiteURL(storeUpdateDTO.getSiteURL());
    selectedStore.setLogoURL(storeUpdateDTO.getLogoURL());
    Store updatedStore = storeRepository.save(selectedStore);
    return mapper.mapToStoreReadOnlyDTO(updatedStore);
  }

  @Transactional(rollbackOn = Exception.class)
  @Override
  public StoreReadOnlyDTO saveStore(StoreInsertDTO dto) throws AppObjectAlreadyExists, AppObjectInvalidArgumentException {
   if(storeRepository.findByName(dto.getName()).isPresent()){
     throw new AppObjectAlreadyExists("Store", "Store named " + dto.getName() + " already exists");
   }
   Store store = mapper.mapToStoreEntity(dto);
   Store savedStore = storeRepository.save(store);
   return mapper.mapToStoreReadOnlyDTO(savedStore);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public StoreReadOnlyDTO deleteStore(Long id) throws AppObjectNotFoundException, AppObjectInvalidArgumentException {
    Optional<Store> optionalStore = storeRepository.findById(id);

    if (optionalStore.isPresent()) {
      Store store = optionalStore.get();
      storeRepository.delete(store);
      return mapper.mapToStoreReadOnlyDTO(store);
    } else {
      throw new AppObjectNotFoundException("Store", "Store with id: " + id + " not found");
    }
  }
//  @Transactional(rollbackOn = Exception.class)
//  @Override
//  public void saveLogoFile(Store store, MultipartFile logoFile) throws IOException {
//    if (logoFile != null && !logoFile.isEmpty()) {
//
//      String originalFileName = logoFile.getOriginalFilename();
//      String savedName = UUID.randomUUID() + getFileExtension(originalFileName);
//      String uploadDir = "uploads/storelogos";
//      Path filepath = Paths.get(uploadDir + savedName);
//      Files.createDirectories(filepath.getParent());
//      Files.write(filepath, logoFile.getBytes());
//
//      Attachment attachment = new Attachment();
//      attachment.setFilename(originalFileName);
//      attachment.setSavedName(savedName);
//      attachment.setFilePath(filepath.toString());
//      attachment.setContentType(logoFile.getContentType());
//      attachment.setExtension(getFileExtension(originalFileName));
//
////      store.setLogo(attachment);
//    }
//  }
  public String getFileExtension(String filename) {
    if (filename != null && filename.contains(".")) {
      return filename.substring(filename.lastIndexOf("."));
    }
    return "";
  }
}
