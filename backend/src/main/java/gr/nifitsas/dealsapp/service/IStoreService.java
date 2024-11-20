package gr.nifitsas.dealsapp.service;


import gr.nifitsas.dealsapp.core.exceptions.AppObjectAlreadyExists;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.dto.StoreInsertDTO;
import gr.nifitsas.dealsapp.model.static_data.Store;

import java.util.List;

public interface IStoreService {
  List<Store> findAllStores();
  Store saveStore(StoreInsertDTO dto) throws AppObjectAlreadyExists, AppObjectInvalidArgumentException;
  String deleteStore(Long id) throws AppObjectNotFoundException, AppObjectInvalidArgumentException;
}
