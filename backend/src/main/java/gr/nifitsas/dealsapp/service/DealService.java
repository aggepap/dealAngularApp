package gr.nifitsas.dealsapp.service;

import gr.nifitsas.dealsapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.nifitsas.dealsapp.core.exceptions.AppObjectNotFoundException;

import gr.nifitsas.dealsapp.core.mapper.Mapper;
import gr.nifitsas.dealsapp.dto.DealInsertDTO;
import gr.nifitsas.dealsapp.dto.DealReadOnlyDTO;
import gr.nifitsas.dealsapp.model.Deal;
import gr.nifitsas.dealsapp.repository.DealRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DealService implements IDealService {

private final DealRepository dealRepository;
private final Mapper mapper;

  @Override
  public List<DealReadOnlyDTO> getDealsByProduct(Long productid) throws AppObjectNotFoundException {
  List<Deal> productDeals = dealRepository.findByProductId(productid);
  return productDeals.stream().map(mapper::mapToDealReadOnlyDTO).collect(Collectors.toList());
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public DealReadOnlyDTO saveDeal(DealInsertDTO dto) throws AppObjectInvalidArgumentException {
      Deal deal = mapper.mapToDealEntity(dto);
      Deal savedDeal = dealRepository.save(deal);
      return mapper.mapToDealReadOnlyDTO(savedDeal);
  }

  @Override
  public Long deleteDeal(Long id) throws AppObjectNotFoundException {
  if(dealRepository.findById(id).isEmpty()) {
    throw new AppObjectNotFoundException("Deal", "Deal with id " + id + " does not exist");
  }
  Long deletedDealId = dealRepository.findById(id).get().getId();
  dealRepository.deleteById(id);
  return deletedDealId;
  }
}
