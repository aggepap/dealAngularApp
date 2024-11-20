package gr.nifitsas.dealsapp.service;


import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;
import gr.nifitsas.dealsapp.dto.DealInsertDTO;
import gr.nifitsas.dealsapp.model.Deal;

import java.util.List;

public interface IDealService {
  List<Deal> getDealsByProduct(Long productid) throws AppObjectNotFoundException;
  Deal saveDeal(DealInsertDTO dto)  throws AppObjectInvalidArgumentException;
  Long deleteDeal(Long id) throws AppObjectNotFoundException;
}
